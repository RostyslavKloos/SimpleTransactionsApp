package ua.rodev.printectestapp.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.rodev.printectestapp.R
import ua.rodev.printectestapp.presentation.NavigationDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    fun goSales() = navigationDispatcher.emit {
        it.navigate(R.id.goSales)
    }

    fun goRefund() = navigationDispatcher.emit {
        it.navigate(R.id.goRefund)
    }

    fun goSettings() = navigationDispatcher.emit {
        it.navigate(R.id.goSettings)
    }
}
