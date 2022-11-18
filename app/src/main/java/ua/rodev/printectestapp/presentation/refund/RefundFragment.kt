package ua.rodev.printectestapp.presentation.refund

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
class RefundFragment : Fragment() {

    private val viewModel: RefundViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {

            val backHandlerEnabled by viewModel.backHandlerEnabled.collectAsStateWithLifecycle()
            val requestResultMessage by viewModel.requestResult.collectAsStateWithLifecycle()
            val progressVisible by viewModel.progressVisible.collectAsStateWithLifecycle()
            val receiptNumber by viewModel.receiptNumber.collectAsStateWithLifecycle()
            val amount by viewModel.amount.collectAsStateWithLifecycle()
            val step by viewModel.step.collectAsStateWithLifecycle()

            val focusManager = LocalFocusManager.current
            BackHandler(enabled = backHandlerEnabled, onBack = viewModel::onBackPressed)

            PrintecTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp),
                        text = stringResource(id = R.string.refund_screen_title),
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
                            if (step == RefundViewModel.STEP_ENTER_RECEIPT) {
                                InputTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp),
                                    text = receiptNumber.value,
                                    error = receiptNumber.error,
                                    labelResId = R.string.placeholder_receipt_number,
                                    onValueChange = viewModel::onReceiptNumberChanged,
                                    keyboardActions = remember {
                                        KeyboardActions {
                                            focusManager.moveFocus(FocusDirection.Next)
                                            viewModel.onNextButtonClicked()
                                        }
                                    },
                                    keyboardOptions = remember {
                                        KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Done
                                        )
                                    }
                                )
                            } else {
                                InputTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp),
                                    text = amount.value,
                                    labelResId = R.string.placeholder_enter_amount,
                                    onValueChange = viewModel::onAmountChanged,
                                    keyboardOptions = remember {
                                        KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        )
                                    },
                                    keyboardActions = remember {
                                        KeyboardActions(
                                            onDone = {
                                                focusManager.moveFocus(FocusDirection.Next)
                                                viewModel.onNextButtonClicked()
                                            }
                                        )
                                    }
                                )
                            }
                            Button(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .width(100.dp)
                                    .padding(end = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Purple500,
                                    contentColor = Color.White
                                ),
                                onClick = viewModel::onNextButtonClicked
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
