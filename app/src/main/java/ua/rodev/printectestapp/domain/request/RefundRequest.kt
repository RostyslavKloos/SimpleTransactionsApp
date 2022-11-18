package ua.rodev.printectestapp.domain.request

data class RefundRequest(
    private val receipt_number: String,
    private val amount: String
)
