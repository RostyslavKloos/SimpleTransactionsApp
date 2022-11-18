package ua.rodev.printectestapp.domain

interface HandleError<T> {
    fun handle(e: Exception): T
}
