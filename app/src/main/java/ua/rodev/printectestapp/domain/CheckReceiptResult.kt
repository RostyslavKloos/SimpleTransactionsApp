package ua.rodev.printectestapp.domain

sealed class CheckReceiptResult {
    object Success : CheckReceiptResult()
    data class Error(val message: String) : CheckReceiptResult()
}
