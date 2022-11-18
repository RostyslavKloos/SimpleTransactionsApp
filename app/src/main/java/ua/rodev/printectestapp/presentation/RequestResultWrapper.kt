package ua.rodev.printectestapp.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestResultWrapper(
    val errorMessage: String = "",
    val successMessage: String = "",
) : Parcelable {

    fun isNotNull() = errorMessage.isNotEmpty() || successMessage.isNotEmpty()
}
