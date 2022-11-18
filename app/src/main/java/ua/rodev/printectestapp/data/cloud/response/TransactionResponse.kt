package ua.rodev.printectestapp.data.cloud.response

data class TransactionResponse(
    private val receiptNumber: String,
    private val amount: String,
    private val currency: String
) {
    interface Mapper<T> {
        fun map(
            receiptNumber: String,
            amount: String,
            currency: String,
        ): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(receiptNumber, amount, currency)
}
