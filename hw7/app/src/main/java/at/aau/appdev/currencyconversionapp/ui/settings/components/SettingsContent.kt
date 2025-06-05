package at.aau.appdev.currencyconversionapp.ui.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.aau.appdev.currencyconversionapp.data.model.UserPreferences
import at.aau.appdev.currencyconversionapp.ui.settings.components.cards.BaseCurrencyCard
import at.aau.appdev.currencyconversionapp.ui.settings.components.cards.CurrentPreferencesCard
import at.aau.appdev.currencyconversionapp.ui.settings.components.cards.DarkModeCard

/**
 * Displays the content of the settings screen including:
 * - Base currency selection
 * - Dark mode toggle
 * - Display of current preferences
 *
 * This Composable groups all setting options into a vertically scrollable column.
 *
 * @param paddingValues Padding from the parent Scaffold.
 * @param availableCurrencies List of available currency codes (e.g., ["USD", "EUR"]).
 * @param userPreferences The current user preferences (base currency, target currency, dark mode).
 * @param onBaseCurrencyChange Callback when the user selects a new base currency.
 * @param onDarkModeToggle Callback when the user toggles dark mode.
 */
@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    availableCurrencies: List<String>,
    userPreferences: UserPreferences,
    onBaseCurrencyChange: (String) -> Unit,
    onDarkModeToggle: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        BaseCurrencyCard(availableCurrencies, userPreferences.baseCurrency, onBaseCurrencyChange)
        DarkModeCard(userPreferences.darkMode, onDarkModeToggle)
        CurrentPreferencesCard(userPreferences)
    }
}