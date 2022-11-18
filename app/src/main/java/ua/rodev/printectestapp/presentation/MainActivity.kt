package ua.rodev.printectestapp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.rodev.printectestapp.R
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher
    private lateinit var navController: NavController
    private val destinationChangedListener =
        NavController.OnDestinationChangedListener { _, _, arguments ->
            val view = findViewById<FragmentContainerView>(R.id.fragmentHost)
            changeSystemBars(view, arguments?.getBoolean("lightBars") ?: true)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        lifecycleScope.apply {
            launch {
                initNavigation()
                withContext(Dispatchers.IO) {
                    launchWhenResumed {
                        navigationDispatcher.collect(navController)
                    }
                }
            }
        }
    }

    private fun initNavigation() {
        (supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment).also { navHost ->
            val navInflater = navHost.navController.navInflater
            val navGraph = navInflater.inflate(R.navigation.main_graph)
            navHost.navController.graph = navGraph
            navController = navHost.navController
            navController.addOnDestinationChangedListener(destinationChangedListener)
        }
    }

    @Suppress("Deprecation")
    private fun changeSystemBars(view: View, light: Boolean) =
        ViewCompat.getWindowInsetsController(view)?.let { controller ->
            if (controller.isAppearanceLightStatusBars != light) {
                controller.isAppearanceLightNavigationBars = light
                controller.isAppearanceLightStatusBars = light
            }
        }
}
