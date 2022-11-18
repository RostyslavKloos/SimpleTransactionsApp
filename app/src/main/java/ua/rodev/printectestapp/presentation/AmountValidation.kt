package ua.rodev.printectestapp.presentation

import androidx.core.text.isDigitsOnly

interface AmountValidation {
    fun isAmount(value: String): Boolean

    class Main : AmountValidation {
        override fun isAmount(value: String): Boolean {
            with(value) {
                return if (contains('.')) {
                    if (length >= MAX_LENGTH_AMOUNT_WITH_DOT) return false
                    matches(Regex(REGEX_INPUT_AMOUNT))
                } else {
                    isDigitsOnly() && length < MAX_LENGTH_AMOUNT
                }
            }
        }

        private companion object {
            const val REGEX_INPUT_AMOUNT = "^\\d+\\.?\\d{0,2}"
            const val MAX_LENGTH_AMOUNT = 11
            const val MAX_LENGTH_AMOUNT_WITH_DOT = MAX_LENGTH_AMOUNT + 3
        }
    }
}
