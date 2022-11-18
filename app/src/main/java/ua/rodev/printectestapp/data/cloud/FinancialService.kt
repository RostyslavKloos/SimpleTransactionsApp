package ua.rodev.printectestapp.data.cloud

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ua.rodev.printectestapp.domain.request.CheckRefundReceiptNumberRequest
import ua.rodev.printectestapp.domain.request.PayRequestModel
import ua.rodev.printectestapp.domain.request.RefundRequest
import ua.rodev.printectestapp.data.cloud.response.TransactionResponse

interface FinancialService {

    @POST("api/printec/financial/pay")
    suspend fun pay(@Body request: PayRequestModel): Response<TransactionResponse>

    @POST("api/printec/financial/refund/receipt-number")
    suspend fun checkRefundReceiptNumber(@Body request: CheckRefundReceiptNumberRequest): Response<Void>

    @POST("api/printec/financial/refund")
    suspend fun refund(@Body request: RefundRequest): Response<TransactionResponse>

}
