package ua.rodev.printectestapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.rodev.printectestapp.core.DispatchersList
import ua.rodev.printectestapp.data.HandleServerError
import ua.rodev.printectestapp.data.MainFinancialRepository
import ua.rodev.printectestapp.data.mappers.TransactionToPayDomainMapper
import ua.rodev.printectestapp.data.mappers.TransactionToRefundDomainMapper
import ua.rodev.printectestapp.data.cloud.FinancialCloudDataSource
import ua.rodev.printectestapp.data.cloud.FinancialService
import ua.rodev.printectestapp.domain.FinancialRepository
import ua.rodev.printectestapp.domain.HandleError
import ua.rodev.printectestapp.domain.SettingsDataStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideDispatchersList(): DispatchersList = DispatchersList.Main()

    @Singleton
    @Provides
    fun provideClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        dataStore: SettingsDataStore,
    ): Retrofit = runBlocking {
        val url = dataStore.readHostURL()
        return@runBlocking Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
    }


    @Provides
    fun provideAuthCalls(retrofit: Retrofit): FinancialService {
        return retrofit.create(FinancialService::class.java)
    }

    @Singleton
    @Provides
    fun provideHandleHandleServerError(): HandleError<Exception> = HandleServerError()

    @Provides
    fun provideFinancialCloudDataSource(
        service: FinancialService,
        handleError: HandleError<Exception>,
    ): FinancialCloudDataSource = FinancialCloudDataSource.Main(service, handleError)

    @Provides
    fun provideRepository(
        cloudDataSource: FinancialCloudDataSource,
        handleError: HandleError<String>,
    ): FinancialRepository = MainFinancialRepository(
        cloudDataSource,
        handleError,
        TransactionToPayDomainMapper(),
        TransactionToRefundDomainMapper()
    )
}
