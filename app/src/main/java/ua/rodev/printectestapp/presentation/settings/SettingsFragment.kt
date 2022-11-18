package ua.rodev.printectestapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ua.rodev.printectestapp.R
import ua.rodev.printectestapp.common.InputTextField
import ua.rodev.printectestapp.theme.PrintecTheme

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {

            val minAmount by viewModel.minAmount.collectAsStateWithLifecycle()
            val maxAmount by viewModel.maxAmount.collectAsStateWithLifecycle()
            val hostUrl by viewModel.hostUrl.collectAsStateWithLifecycle()
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val keyboardOptions = remember {
                KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            }
            val keyboardActions = remember {
                KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            }

            PrintecTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.minumum_amount))
                        Spacer(Modifier.width(16.dp))
                        InputTextField(
                            text = minAmount,
                            onValueChange = viewModel::onMinAmountChanged,
                            keyboardOptions = keyboardOptions,
                            keyboardActions = keyboardActions
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically
                    ) {
                        Text(text = stringResource(R.string.maximum_amount))
                        Spacer(Modifier.width(16.dp))
                        InputTextField(
                            text = maxAmount,
                            onValueChange = viewModel::onMaxAmountChanged,
                            keyboardOptions = keyboardOptions,
                            keyboardActions = keyboardActions
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = stringResource(R.string.communication_host_url))
                    InputTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = hostUrl,
                        onValueChange = viewModel::onHostUrlChanged,
                        keyboardOptions = remember {
                            KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        },
                        keyboardActions = remember {
                            KeyboardActions(onDone = { keyboardController?.hide() })
                        }
                    )
                }
            }
        }
    }
}
