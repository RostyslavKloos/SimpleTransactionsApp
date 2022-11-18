package ua.rodev.printectestapp.common

import android.os.SystemClock
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.rodev.printectestapp.theme.Purple500

typealias OnClick = () -> Unit

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    backgroundColor: Color = Purple500,
    textColor: Color = Color.White,
    @StringRes stringId: Int,
    enabled: Boolean = true,
    onClick: OnClick,
) {
    var lastClickTime by remember { mutableStateOf(0L) }
    Button(
        onClick = {
            if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return@Button
            lastClickTime = SystemClock.elapsedRealtime()
            onClick()
        },
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = textColor
        ),
        elevation = ButtonDefaults.elevation(elevation),
        enabled = enabled
    ) {
        Text(
            text = stringResource(stringId),
            modifier = modifier,
            style = MaterialTheme.typography.button,
            color = textColor,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}
