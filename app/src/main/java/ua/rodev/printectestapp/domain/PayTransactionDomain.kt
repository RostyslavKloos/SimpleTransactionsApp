package ua.rodev.printectestapp.domain

data class PayTransactionDomain(
    private val receiptNumber: String,
    private val amount: String,
    private val currency: String,
) {
    fun toUi(): String {
        return "Transaction result approved! \nReceipt number: $receiptNumber\nAmount: $amount, $currency"
    }
}
