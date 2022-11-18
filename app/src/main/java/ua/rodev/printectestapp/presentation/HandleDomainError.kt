package ua.rodev.printectestapp.presentation

import ua.rodev.printectestapp.R
import ua.rodev.printectestapp.domain.DomainException
import ua.rodev.printectestapp.domain.HandleError

class HandleDomainError(private val manageResources: ManageResources) : HandleError<String> {

    override fun handle(e: Exception): String {
        return when (e) {
            is DomainException.NoInternetConnection -> manageResources.string(R.string.error_no_internet_connection)
            is DomainException.WrongReceiptNumber -> manageResources.string(R.string.error_wrong_receipt_number)
            else -> manageResources.string(R.string.service_is_unavailable)
        }
    }
}
