package ua.rodev.printectestapp.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputWrapper(
    var value: String = "",
    var error: String = ""
) : Parcelable
