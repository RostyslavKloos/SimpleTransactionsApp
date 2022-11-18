package ua.rodev.printectestapp.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ua.rodev.printectestapp.presentation.RequestResultWrapper

@Composable
fun RequestResultMessage(
    modifier: Modifier = Modifier,
    requestResultMessage: RequestResultWrapper,
) {
    if (requestResultMessage.errorMessage.isEmpty()) {
        Box(modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = requestResultMessage.successMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
            )
        }
    }
    else {
        Box(modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = requestResultMessage.errorMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
            )
        }
    }
}
