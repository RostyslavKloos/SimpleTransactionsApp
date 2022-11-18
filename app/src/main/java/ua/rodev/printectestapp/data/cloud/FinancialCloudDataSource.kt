package ua.rodev.printectestapp.data.cloud

import retrofit2.http.Body
import ua.rodev.printectestapp.data.cloud.response.TransactionResponse
import ua.rodev.printectestapp.domain.DomainException
import ua.rodev.printectestapp.domain.HandleError
import ua.rodev.printectestapp.domain.request.CheckRefundReceiptNumberRequest
import ua.rodev.printectestapp.domain.request.PayRequestModel
import ua.rodev.printectestapp.domain.request.RefundRequest

interface FinancialCloudDataSource {

    suspend fun pay(request: PayRequestModel): TransactionResponse
    suspend fun checkRefundReceiptNumber(@Body request: CheckRefundReceiptNumberRequest): Boolean
    suspend fun refund(request: RefundRequest): TransactionResponse

    class Main(
        private val service: FinancialService,
        private val handleError: HandleError<Exception>,
    ) : FinancialCloudDataSource {

        @Throws(DomainException::class)
        override suspend fun pay(request: PayRequestModel): TransactionResponse {
            try {
                val response = service.pay(request)
                return response.body() ?: throw DomainException.ServiceUnavailable
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }

        @Throws(DomainException::class)
        override suspend fun checkRefundReceiptNumber(request: CheckRefundReceiptNumberRequest): Boolean {
            try {
                val response = service.checkRefundReceiptNumber(request)
                if (response.code() == 400) throw DomainException.WrongReceiptNumber
                return true
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }

        @Throws(DomainException::class)
        override suspend fun refund(request: RefundRequest): TransactionResponse {
            try {
                val response = service.refund(request)
                return response.body() ?: throw DomainException.ServiceUnavailable
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }
    }
}
