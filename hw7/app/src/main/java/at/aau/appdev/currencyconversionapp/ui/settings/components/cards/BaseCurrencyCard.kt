package at.aau.appdev.currencyconversionapp.ui.settings.components.cards

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A card component that allows the user to select their preferred base currency.
 *
 * This card wraps the [BaseCurrencyContent] inside a [Card] layout and
 * provides a full-width container for consistent UI spacing and styling.
 *
 * @param availableCurrencies The list of currency abbreviations to choose from (e.g., ["USD", "EUR"]).
 * @param selectedCurrency The currently selected base currency.
 * @param onCurrencySelected Callback triggered when the user selects a new base currency.
 */
@Composable
fun BaseCurrencyCard(
    availableCurrencies: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        BaseCurrencyContent(
            availableCurrencies = availableCurrencies,
            selectedCurrency = selectedCurrency,
            onCurrencySelected = onCurrencySelected
        )
    }
}

