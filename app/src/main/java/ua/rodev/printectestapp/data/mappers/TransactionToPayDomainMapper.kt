package ua.rodev.printectestapp.data.mappers

import ua.rodev.printectestapp.data.cloud.response.TransactionResponse
import ua.rodev.printectestapp.domain.PayTransactionDomain

class TransactionToPayDomainMapper : TransactionResponse.Mapper<PayTransactionDomain> {
    override fun map(
        receiptNumber: String,
        amount: String,
        currency: String,
    ): PayTransactionDomain {
        return PayTransactionDomain(receiptNumber, amount, currency)
    }
}
