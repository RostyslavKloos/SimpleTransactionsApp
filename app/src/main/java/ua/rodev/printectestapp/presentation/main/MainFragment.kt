package ua.rodev.printectestapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.rodev.printectestapp.R
import ua.rodev.printectestapp.common.PrimaryButton
import ua.rodev.printectestapp.theme.PrintecTheme

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            PrintecTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 50.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally,
                ) {
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = viewModel::goSales,
                        stringId = R.string.sales_screen_title
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = viewModel::goRefund,
                        stringId = R.string.refund_screen_title
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = viewModel::goSettings,
                        stringId = R.string.settings_screen_title
                    )
                }
            }
        }
    }
}
