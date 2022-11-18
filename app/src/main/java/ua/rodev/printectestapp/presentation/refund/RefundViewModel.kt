package ua.rodev.printectestapp.presentation.refund

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.rodev.printectestapp.presentation.AmountValidation
import ua.rodev.printectestapp.presentation.RequestResultWrapper
import ua.rodev.printectestapp.domain.CheckReceiptResult
import ua.rodev.printectestapp.domain.FinancialRepository
import ua.rodev.printectestapp.domain.TransactionResult
import ua.rodev.printectestapp.domain.request.CheckRefundReceiptNumberRequest
import ua.rodev.printectestapp.domain.request.RefundRequest
import ua.rodev.printectestapp.presentation.InputWrapper
import javax.inject.Inject

@HiltViewModel
class RefundViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: FinancialRepository,
    private val amountValidation: AmountValidation,
) : ViewModel() {

    val step = handle.getStateFlow(STEP, STEP_ENTER_RECEIPT)
    private val _progressVisible = MutableStateFlow(false)
    val progressVisible = _progressVisible.asStateFlow()
    val receiptNumber = handle.getStateFlow(RECEIPT_NUMBER, InputWrapper())
    val amount = handle.getStateFlow(AMOUNT, InputWrapper())
    val requestResult = handle.getStateFlow(REQUEST_RESULT, RequestResultWrapper())
    val backHandlerEnabled = step.map {
        it == STEP_ENTER_AMOUNT
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true
    )

    fun onAmountChanged(value: String) {
        if (amountValidation.isAmount(value)) {
            handle[AMOUNT] = amount.value.copy(value = value)
        }
    }

    fun onReceiptNumberChanged(value: String) {
        handle[RECEIPT_NUMBER] = receiptNumber.value.copy(value = value, error = "")
    }

    fun onNextButtonClicked() {
        if (step.value == STEP_ENTER_RECEIPT) {
            viewModelScope.launch {
                _progressVisible.value = true
                val request = CheckRefundReceiptNumberRequest(receiptNumber.value.value)
                when (val result = repository.checkRefundReceiptNumber(request)) {
                    is CheckReceiptResult.Error -> {
                        _progressVisible.value = false
                        handle[RECEIPT_NUMBER] = receiptNumber.value.copy(error = result.message)
                    }
                    CheckReceiptResult.Success -> {
                        _progressVisible.value = false
                        handle[STEP] = STEP_ENTER_AMOUNT
                    }
                }
            }
        } else {
            viewModelScope.launch {
                _progressVisible.value = true
                val request = RefundRequest(receiptNumber.value.value, amount.value.value)
                when (val result = repository.refund(request)) {
                    is TransactionResult.Error -> {
                        _progressVisible.value = false
                        handle[REQUEST_RESULT] = requestResult.value.copy(errorMessage = result.message)
                    }
                    is TransactionResult.Success -> {
                        _progressVisible.value = false
                        handle[REQUEST_RESULT] =
                            requestResult.value.copy(successMessage = result.data.toUi())
                    }
                }
            }
        }
    }

    fun onBackPressed() {
        handle[STEP] = STEP_ENTER_RECEIPT
    }

    companion object {
        const val STEP = "step"
        const val STEP_ENTER_RECEIPT = 0
        const val STEP_ENTER_AMOUNT = 1
        private const val RECEIPT_NUMBER = "receiptNumber"
        private const val AMOUNT = "amount"
        private const val REQUEST_RESULT = "requestResult"
    }
}
