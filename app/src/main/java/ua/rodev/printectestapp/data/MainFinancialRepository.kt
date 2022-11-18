package ua.rodev.printectestapp.data

import ua.rodev.printectestapp.data.cloud.FinancialCloudDataSource
import ua.rodev.printectestapp.data.cloud.response.TransactionResponse
import ua.rodev.printectestapp.domain.*
import ua.rodev.printectestapp.domain.request.CheckRefundReceiptNumberRequest
import ua.rodev.printectestapp.domain.request.PayRequestModel
import ua.rodev.printectestapp.domain.request.RefundRequest

class MainFinancialRepository(
    private val cloudDataSource: FinancialCloudDataSource,
    private val handleDomainError: HandleError<String>,
    private val payDomainMapper: TransactionResponse.Mapper<PayTransactionDomain>,
    private val transactionDomainMapper: TransactionResponse.Mapper<RefundTransactionDomain>,
) : FinancialRepository {

    override suspend fun pay(request: PayRequestModel): TransactionResult<PayTransactionDomain> {
        return try {
            val result = cloudDataSource.pay(request)
            val domain = result.map(payDomainMapper)
            TransactionResult.Success(domain)
        } catch (e: Exception) {
            TransactionResult.Error(handleDomainError.handle(e))
        }
    }

    override suspend fun checkRefundReceiptNumber(request: CheckRefundReceiptNumberRequest): CheckReceiptResult {
        return try {
            cloudDataSource.checkRefundReceiptNumber(request)
            CheckReceiptResult.Success
        } catch (e: Exception) {
            CheckReceiptResult.Error(handleDomainError.handle(e))
        }
    }

    override suspend fun refund(request: RefundRequest): TransactionResult<RefundTransactionDomain> {
        return try {
            val result = cloudDataSource.refund(request)
            val domain = result.map(transactionDomainMapper)
            TransactionResult.Success(domain)
        } catch (e: Exception) {
            TransactionResult.Error(handleDomainError.handle(e))
        }
    }
}
