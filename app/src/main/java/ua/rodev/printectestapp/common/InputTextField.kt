package ua.rodev.printectestapp.common

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.rodev.printectestapp.theme.ErrorColor
import ua.rodev.printectestapp.theme.Text400Color
import ua.rodev.printectestapp.theme.Text500Color

typealias OnValueChange = (value: String) -> Unit

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    text: String,
    error: String = "",
    onValueChange: OnValueChange = {},
    @StringRes labelResId: Int? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    isEnabled: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
) {
    var hasFocus by remember { mutableStateOf(false) }

    Column {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged {
                    hasFocus = it.isFocused
                    if (hasFocus) onValueChange(text)
                },
            value = text,
            onValueChange = onValueChange,
            label = {
                if (labelResId != null) {
                    Text(
                        text = stringResource(labelResId),
                        style = when (hasFocus || text.isNotBlank()) {
                            true -> MaterialTheme.typography.caption
                            false -> MaterialTheme.typography.body2.copy(fontSize = 16.sp)
                        }
                    )
                }
            },
            colors = filledTextFieldColors(),
            isError = error.isNotEmpty(),
            textStyle = MaterialTheme.typography.body1,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            enabled = isEnabled,
            maxLines = maxLines,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
        )
        if (error.isNotEmpty()) TextFieldErrorMessage(error)
    }
}

@Composable
fun TextFieldErrorMessage(error: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
        text = error,
        color = ErrorColor,
        style = MaterialTheme.typography.caption.copy(
            fontWeight = FontWeight.W400
        )
    )
}

@Composable
fun filledTextFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    textColor = Text500Color,
    errorBorderColor = ErrorColor,
    errorCursorColor = ErrorColor,
    errorLabelColor = ErrorColor,
    errorLeadingIconColor = Text400Color,
    errorTrailingIconColor = Text400Color,
)
