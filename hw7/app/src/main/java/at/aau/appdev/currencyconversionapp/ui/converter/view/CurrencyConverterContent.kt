import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencyConverterUiState
import at.aau.appdev.currencyconversionapp.ui.converter.view.components.AmountInputField
import at.aau.appdev.currencyconversionapp.ui.converter.view.components.ConversionStatusSection
import at.aau.appdev.currencyconversionapp.ui.converter.view.components.ConvertButton
import at.aau.appdev.currencyconversionapp.ui.converter.view.components.ConvertedResultCard
import at.aau.appdev.currencyconversionapp.ui.converter.view.components.CurrencyConverterHeader
import at.aau.appdev.currencyconversionapp.ui.converter.view.components.CurrencySelectorsSection

/**
 * Main content layout for the Currency Converter screen.
 *
 * This composable combines all major UI components such as:
 * - Header with navigation buttons
 * - Amount input field
 * - Currency selectors (from/to)
 * - Convert button
 * - Status section (loading/success/error)
 * - Converted result card
 *
 * It delegates visual structure and logic to reusable sub-components
 * for improved readability and maintainability.
 *
 * @param paddingValues Outer padding to apply around the entire screen.
 * @param uiState The current state of the currency converter UI (loading, success, or error).
 * @param amountInput The current user input for the amount to be converted.
 * @param onAmountInputChange Callback invoked when the user updates the amount input.
 * @param availableCurrencies List of currency codes available for selection.
 * @param selectedFromCurrency Currently selected source currency.
 * @param onFromCurrencySelected Callback when a new source currency is selected.
 * @param selectedToCurrency Currently selected target currency.
 * @param onToCurrencySelected Callback when a new target currency is selected.
 * @param onConvertClick Callback invoked when the "Convert" button is clicked.
 * @param convertedResult The result of the currency conversion (formatted).
 * @param statusMessage A message to display regarding conversion status or results.
 * @param onRetryFetch Callback invoked when the user retries loading rates after an error.
 * @param onShowSettingsClick Callback invoked when the user opens the settings screen.
 * @param onShowLogClick Callback invoked when the user opens the conversion log screen.
 */
@Composable
fun CurrencyConverterContent(
    paddingValues: PaddingValues,
    uiState: CurrencyConverterUiState,
    amountInput: String,
    onAmountInputChange: (String) -> Unit,
    availableCurrencies: List<String>,
    selectedFromCurrency: String,
    onFromCurrencySelected: (String) -> Unit,
    selectedToCurrency: String,
    onToCurrencySelected: (String) -> Unit,
    onConvertClick: () -> Unit,
    convertedResult: String,
    statusMessage: String,
    onRetryFetch: () -> Unit,
    onShowSettingsClick: () -> Unit,
    onShowLogClick: () -> Unit
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            CurrencyConverterHeader(
                onShowLogClick = onShowLogClick,
                onShowSettingsClick = onShowSettingsClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            AmountInputField(
                amount = amountInput,
                onAmountChange = onAmountInputChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CurrencySelectorsSection(
                uiState = uiState,
                availableCurrencies = availableCurrencies,
                selectedFromCurrency = selectedFromCurrency,
                onFromCurrencySelected = onFromCurrencySelected,
                selectedToCurrency = selectedToCurrency,
                onToCurrencySelected = onToCurrencySelected
            )

            Spacer(modifier = Modifier.height(24.dp))

            ConvertButton(
                enabled = uiState is CurrencyConverterUiState.Success &&
                        amountInput.toDoubleOrNull() != null &&
                        amountInput.toDouble() > 0,
                onClick = onConvertClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            ConversionStatusSection(
                uiState = uiState,
                statusMessage = statusMessage,
                onRetryFetch = onRetryFetch
            )

            ConvertedResultCard(convertedResult = convertedResult)
        }
    }
}
