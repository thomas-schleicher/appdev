package at.aau.appdev.currencyconversionapp.ui.settings.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencySelector
import at.aau.appdev.currencyconversionapp.ui.settings.components.common.CardTitle
import at.aau.appdev.currencyconversionapp.ui.settings.components.common.DescriptionText

/**
 * Displays the content inside the Base Currency card.
 *
 * This composable shows a title, description, and a currency selector dropdown
 * that allows the user to choose their preferred base currency.
 *
 * @param availableCurrencies A list of supported currency abbreviations.
 * @param selectedCurrency The currently selected base currency.
 * @param onCurrencySelected Callback triggered when the user selects a different currency.
 */
@Composable
fun BaseCurrencyContent(
    availableCurrencies: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        CardTitle("Base Currency")
        Spacer(modifier = Modifier.height(8.dp))
        DescriptionText("Your preferred base currency for conversions")
        Spacer(modifier = Modifier.height(16.dp))

        CurrencySelectorWithFallback(
            label = "Select Base Currency",
            selectedCurrency = selectedCurrency,
            availableCurrencies = availableCurrencies,
            onCurrencySelected = onCurrencySelected
        )
    }
}

/**
 * Displays a [CurrencySelector] if currency data is available, or a fallback loading message.
 *
 * This is useful for cases where the currency list is loaded asynchronously,
 * and a fallback message is needed during the loading state.
 *
 * @param label The label for the currency selector.
 * @param selectedCurrency The currently selected currency.
 * @param availableCurrencies The list of available currencies.
 * @param onCurrencySelected Callback triggered when a new currency is selected.
 */
@Composable
fun CurrencySelectorWithFallback(
    label: String,
    selectedCurrency: String,
    availableCurrencies: List<String>,
    onCurrencySelected: (String) -> Unit
) {
    if (availableCurrencies.isNotEmpty()) {
        CurrencySelector(
            label = label,
            selectedCurrency = selectedCurrency,
            currencies = availableCurrencies,
            onCurrencySelected = onCurrencySelected,
            isEnabled = true
        )
    } else {
        Text(
            text = "Loading currencies...",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}