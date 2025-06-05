package at.aau.appdev.currencyconversionapp.ui.settings

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import at.aau.appdev.currencyconversionapp.data.model.UserPreferences
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencyConverterViewModel
import at.aau.appdev.currencyconversionapp.ui.settings.components.SettingsContent
import at.aau.appdev.currencyconversionapp.ui.settings.components.SettingsTopAppBar

/**
 * Composable that represents the settings screen of the currency conversion app.
 *
 * It displays user preferences such as:
 * - Base currency selection
 * - Dark mode toggle
 * - Current saved preferences
 *
 * This screen observes [CurrencyConverterViewModel.userPreferences] and reflects changes in the UI.
 *
 * @param viewModel The ViewModel used to access and update user preferences.
 * @param onBackClick Callback triggered when the user taps the back button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: CurrencyConverterViewModel,
    onBackClick: () -> Unit
) {
    // Observe user preferences state from DataStore
    val userPreferences by viewModel.userPreferences.collectAsState(
        initial = UserPreferences("USD", "EUR", false)
    )

    // Get the list of currencies available for selection
    val availableCurrencies = viewModel.getAvailableCurrencies()

    // Build the screen layout with a top app bar and content
    Scaffold(
        topBar = { SettingsTopAppBar(onBackClick) }
    ) { paddingValues ->
        SettingsContent(
            paddingValues = paddingValues,
            availableCurrencies = availableCurrencies,
            userPreferences = userPreferences,
            onBaseCurrencyChange = viewModel::saveBaseCurrency,
            onDarkModeToggle = viewModel::saveDarkMode
        )
    }
}