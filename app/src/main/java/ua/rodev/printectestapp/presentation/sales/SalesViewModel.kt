package ua.rodev.printectestapp.presentation.sales

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.rodev.printectestapp.R
import ua.rodev.printectestapp.core.DispatchersList
import ua.rodev.printectestapp.presentation.RequestResultWrapper
import ua.rodev.printectestapp.domain.FinancialRepository
import ua.rodev.printectestapp.domain.SettingsDataStore
import ua.rodev.printectestapp.domain.TransactionResult
import ua.rodev.printectestapp.domain.request.PayRequestModel
import ua.rodev.printectestapp.presentation.AmountValidation
import ua.rodev.printectestapp.presentation.InputWrapper
import ua.rodev.printectestapp.presentation.ManageResources
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val amountValidation: AmountValidation,
    private val manageResources: ManageResources,
    private val repository: FinancialRepository,
    private val handle: SavedStateHandle,
    dispatchersList: DispatchersList,
) : ViewModel() {

    private val minAmount = MutableStateFlow(0.0)
    private val maxAmount: MutableStateFlow<Double?> = MutableStateFlow(null)
    private val _progressVisible = MutableStateFlow(false)
    val progressVisible = _progressVisible.asStateFlow()
    val requestResult = handle.getStateFlow(REQUEST_RESULT, RequestResultWrapper())
    val amount = handle.getStateFlow(AMOUNT, InputWrapper())

    init {
        viewModelScope.launch(dispatchersList.io()) {
            settingsDataStore.readSalesMinAmount()?.let { minAmount.value = it }
            settingsDataStore.readSalesMaxAmount()?.let { maxAmount.value = it }
        }
    }

    fun onAmountChanged(value: String) {
        if (amountValidation.isAmount(value)) {
            val currentValue = value.toDoubleOrNull() ?: 0.0
            if (currentValue < minAmount.value) {
                handle[AMOUNT] = amount.value.copy(
                    value = value,
                    error = manageResources.string(R.string.error_min_value, minAmount.value.toString())
                )
            } else if (maxAmount.value != null && currentValue > maxAmount.value!!) {
                handle[AMOUNT] = amount.value.copy(
                    value = value,
                    error = manageResources.string(R.string.error_max_value, maxAmount.value.toString())
                )
            } else {
                handle[AMOUNT] = amount.value.copy(value = value, error = "")
            }
        }
    }

    fun onNextButtonClicked() {
        if (amount.value.value.isNotEmpty()) {
            viewModelScope.launch {
                _progressVisible.value = true
                when (val result = repository.pay(PayRequestModel(amount.value.value))) {
                    is TransactionResult.Error -> {
                        _progressVisible.value = false
                        handle[REQUEST_RESULT] = RequestResultWrapper(errorMessage = result.message)
                    }
                    is TransactionResult.Success -> {
                        _progressVisible.value = false
                        handle[REQUEST_RESULT] = RequestResultWrapper(successMessage = result.data.toUi())
                    }
                }
            }
        }
    }

    companion object {
        private const val AMOUNT = "amount"
        private const val REQUEST_RESULT = "requestResult"
    }
}
