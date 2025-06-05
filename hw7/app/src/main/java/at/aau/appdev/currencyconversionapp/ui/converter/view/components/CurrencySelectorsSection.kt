package at.aau.appdev.currencyconversionapp.ui.converter.view.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencyConverterUiState
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencySelector

/**
 * Section displaying the currency selectors for source and target currencies.
 *
 * Includes two [CurrencySelector] components:
 * - One for selecting the "From" (base) currency
 * - One for selecting the "To" (target) currency
 *
 * Both selectors are disabled while the UI is in a loading state or if no currencies are available.
 *
 * @param uiState The current UI state used to determine whether the selectors are enabled.
 * @param availableCurrencies A list of currency codes available for selection.
 * @param selectedFromCurrency The currently selected base currency.
 * @param onFromCurrencySelected Callback invoked when the base currency changes.
 * @param selectedToCurrency The currently selected target currency.
 * @param onToCurrencySelected Callback invoked when the target currency changes.
 */
@Composable
fun CurrencySelectorsSection(
    uiState: CurrencyConverterUiState,
    availableCurrencies: List<String>,
    selectedFromCurrency: String,
    onFromCurrencySelected: (String) -> Unit,
    selectedToCurrency: String,
    onToCurrencySelected: (String) -> Unit
) {
    val enabled = uiState !is CurrencyConverterUiState.Loading && availableCurrencies.isNotEmpty()

    CurrencySelector(
        label = "From Currency",
        selectedCurrency = selectedFromCurrency,
        currencies = availableCurrencies,
        onCurrencySelected = onFromCurrencySelected,
        isEnabled = enabled
    )

    Spacer(modifier = Modifier.height(16.dp))

    CurrencySelector(
        label = "To Currency",
        selectedCurrency = selectedToCurrency,
        currencies = availableCurrencies,
        onCurrencySelected = onToCurrencySelected,
        isEnabled = enabled
    )
}
