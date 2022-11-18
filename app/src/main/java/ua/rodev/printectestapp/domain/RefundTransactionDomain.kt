package ua.rodev.printectestapp.domain

data class RefundTransactionDomain(
    private val receiptNumber: String,
    private val amount: String,
    private val currency: String,
) {
    fun toUi(): String {
        return "Refund approved! \nReceipt number: $receiptNumber\nAmount: $amount, $currency"
    }
}
