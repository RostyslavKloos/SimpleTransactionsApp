package ua.rodev.printectestapp.data.mappers

import ua.rodev.printectestapp.data.cloud.response.TransactionResponse
import ua.rodev.printectestapp.domain.RefundTransactionDomain


class TransactionToRefundDomainMapper : TransactionResponse.Mapper<RefundTransactionDomain> {
    override fun map(
        receiptNumber: String,
        amount: String,
        currency: String,
    ): RefundTransactionDomain {
        return RefundTransactionDomain(receiptNumber, amount, currency)
    }
}
