package ua.rodev.printectestapp.presentation.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ua.rodev.printectestapp.R
import ua.rodev.printectestapp.common.InputTextField
import ua.rodev.printectestapp.common.RequestResultMessage
import ua.rodev.printectestapp.theme.PrintecTheme
import ua.rodev.printectestapp.theme.Purple500

@AndroidEntryPoint
class SalesFragment : Fragment() {

    private val viewModel: SalesViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {

            val requestResultMessage by viewModel.requestResult.collectAsStateWithLifecycle()
            val progressVisible by viewModel.progressVisible.collectAsStateWithLifecycle()
            val amount by viewModel.amount.collectAsStateWithLifecycle()

            PrintecTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp),
                        text = stringResource(id = R.string.sales_screen_title),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center
                    )
                    when {
                        progressVisible -> {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize(),
                                content = { CircularProgressIndicator() }
                            )
                        }
                        requestResultMessage.isNotNull() -> {
                            RequestResultMessage(
                                requestResultMessage = requestResultMessage
                            )
                        }
                        else -> {
                            Spacer(Modifier.height(100.dp))
                            InputTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                text = amount.value,
                                error = amount.error,
                                onValueChange = viewModel::onAmountChanged,
                                labelResId = R.string.placeholder_enter_amount,
                                keyboardOptions = remember {
                                    KeyboardOptions(keyboardType = KeyboardType.Number)
                                }
                            )
                            Button(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .width(100.dp)
                                    .padding(end = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Purple500,
                                    contentColor = Color.White
                                ),
                                onClick = viewModel::onNextButtonClicked,
                                ) {
                                Text(text = stringResource(R.string.next))
                            }
                        }
                    }
                }
            }
        }
    }
}
