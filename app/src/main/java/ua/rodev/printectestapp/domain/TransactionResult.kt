package ua.rodev.printectestapp.domain

sealed class TransactionResult<out T> {
    data class Success<out T>(val data: T) : TransactionResult<T>()
    data class Error(val message: String) : TransactionResult<Nothing>()
}
