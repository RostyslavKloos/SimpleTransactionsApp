package ua.rodev.printectestapp.data

import ua.rodev.printectestapp.domain.DomainException
import ua.rodev.printectestapp.domain.HandleError
import java.net.UnknownHostException

class HandleServerError : HandleError<Exception> {
    override fun handle(e: Exception): DomainException = when (e) {
        is UnknownHostException -> DomainException.NoInternetConnection
        is DomainException -> e
        else -> DomainException.ServiceUnavailable
    }
}
