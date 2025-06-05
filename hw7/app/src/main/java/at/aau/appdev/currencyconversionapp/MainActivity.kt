package at.aau.appdev.currencyconversionapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import at.aau.appdev.currencyconversionapp.data.model.UserPreferences
import at.aau.appdev.currencyconversionapp.di.DatabaseProvider
import at.aau.appdev.currencyconversionapp.ui.converter.view.CurrencyConverterScreen
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencyConverterViewModel
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencyConverterViewModelFactory
import at.aau.appdev.currencyconversionapp.ui.theme.CurrencyConversionAppTheme

/**
 * The main entry point of the currency conversion app.
 *
 * This activity is responsible for:
 * - Creating the ViewModel using a factory
 * - Collecting user preferences from the ViewModel (e.g., dark mode)
 * - Applying the selected theme
 * - Displaying the currency converter screen within a [Scaffold] layout
 */
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is starting.
     * Sets up edge-to-edge layout, initializes the ViewModel, and defines the app UI using Jetpack Compose.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this
     *                           contains the saved state. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModelFactory = provideCurrencyConverterViewModelFactory(applicationContext)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Get ViewModel and observe user preferences from DataStore
            val viewModel: CurrencyConverterViewModel = viewModel(factory = viewModelFactory)
            val userPreferences by viewModel.userPreferences.collectAsState(
                initial = UserPreferences("USD", "EUR", false)
            )

            // Apply app theme based on user preference and load UI
            CurrencyConversionAppTheme(darkTheme = userPreferences.darkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterScreen(viewModel, innerPadding)
                }
            }
        }
    }

    /**
     * Provides a [ViewModelProvider.Factory] to create the [CurrencyConverterViewModel].
     *
     * This method injects the required [CurrencyRateDAO] dependency using [DatabaseProvider].
     *
     * @param context The application context used to access the database.
     * @return A factory that can create [CurrencyConverterViewModel] instances.
     */
    fun provideCurrencyConverterViewModelFactory(context: Context): ViewModelProvider.Factory {
        val dao = DatabaseProvider.provideCurrencyRateDao(context)
        return CurrencyConverterViewModelFactory(dao, context)
    }
}