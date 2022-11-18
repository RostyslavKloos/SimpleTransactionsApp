package ua.rodev.printectestapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.rodev.printectestapp.core.DispatchersList
import ua.rodev.printectestapp.domain.SettingsDataStore
import ua.rodev.printectestapp.presentation.AmountValidation
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val amountValidation: AmountValidation,
    private val dataStore: SettingsDataStore,
    dispatchersList: DispatchersList,
) : ViewModel() {

    private val _minAmount = MutableStateFlow("")
    val minAmount = _minAmount.asStateFlow()
    private val _maxAmount = MutableStateFlow("")
    val maxAmount = _maxAmount.asStateFlow()
    private val _hostUrl = MutableStateFlow("")
    val hostUrl = _hostUrl.asStateFlow()

    init {
        viewModelScope.launch(dispatchersList.io()) {
            dataStore.readSalesMinAmount()?.let {
                _minAmount.value = it.toString()
            }
            dataStore.readSalesMaxAmount()?.let {
                _maxAmount.value = it.toString()
            }
            _hostUrl.value = dataStore.readHostURL()
        }
    }

    fun onMinAmountChanged(value: String) {
        if (amountValidation.isAmount(value)) {
            viewModelScope.launch {
                _minAmount.value = value
                dataStore.saveSalesMinAmount(value.toDoubleOrNull())
            }
        }
    }

    fun onMaxAmountChanged(value: String) {
        if (amountValidation.isAmount(value)) {
            viewModelScope.launch {
                _maxAmount.value = value
                dataStore.saveSalesMaxAmount(value.toDoubleOrNull())
            }
        }
    }

    fun onHostUrlChanged(value: String) {
        viewModelScope.launch {
            _hostUrl.value = value
            dataStore.saveHostURL(value)
        }
    }
}
