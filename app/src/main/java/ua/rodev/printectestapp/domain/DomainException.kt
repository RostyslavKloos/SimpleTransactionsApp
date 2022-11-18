package ua.rodev.printectestapp.domain

sealed class DomainException : IllegalStateException() {
    object NoInternetConnection : DomainException()
    object ServiceUnavailable : DomainException()
    object WrongReceiptNumber : DomainException()
}
