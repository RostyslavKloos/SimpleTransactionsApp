package ua.rodev.printectestapp.domain

import ua.rodev.printectestapp.domain.request.CheckRefundReceiptNumberRequest
import ua.rodev.printectestapp.domain.request.PayRequestModel
import ua.rodev.printectestapp.domain.request.RefundRequest

interface FinancialRepository {
    suspend fun pay(request: PayRequestModel): TransactionResult<PayTransactionDomain>
    suspend fun checkRefundReceiptNumber(request: CheckRefundReceiptNumberRequest): CheckReceiptResult
    suspend fun refund(request: RefundRequest): TransactionResult<RefundTransactionDomain>
}
