package at.aau.appdev.currencyconversionapp.ui.converter.view

import CurrencyConverterContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import at.aau.appdev.currencyconversionapp.data.model.UserPreferences
import at.aau.appdev.currencyconversionapp.domain.ConversionLogger
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencyConverterUiState
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencyConverterViewModel
import at.aau.appdev.currencyconversionapp.ui.log.ConversionHistoryScreen
import at.aau.appdev.currencyconversionapp.ui.settings.SettingsScreen
import java.text.DecimalFormat

/**
 * Composable screen for converting currencies.
 *
 * Displays the main UI for currency conversion including settings and log views.
 * Reacts to state changes from the ViewModel and passes necessary parameters
 * to the UI content function.
 *
 * @param viewModel The ViewModel managing UI state and conversion logic.
 * @param paddingValues The padding to apply around the content.
 */
@Composable
fun CurrencyConverterScreen(viewModel: CurrencyConverterViewModel, paddingValues: PaddingValues) {
    // State declarations
    val uiState by viewModel.uiState.observeAsState(initial = CurrencyConverterUiState.Loading)
    val userPreferences by viewModel.userPreferences.collectAsState(
        initial = UserPreferences("USD", "EUR", false)
    )

    var amountInput by remember { mutableStateOf("") }
    var selectedFromCurrency by remember { mutableStateOf("USD") }
    var selectedToCurrency by remember { mutableStateOf("EUR") }
    var convertedResult by remember { mutableStateOf<String>("") }
    var statusMessage by remember { mutableStateOf("Ready to convert") }
    var showSettings by remember { mutableStateOf(false) }
    var showLog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val availableCurrencies = extractAvailableCurrencies(uiState)

    LaunchedEffect(userPreferences) {
        selectedFromCurrency = userPreferences.baseCurrency
        selectedToCurrency = userPreferences.lastTargetCurrency
    }

    when {
        showSettings -> {
            SettingsScreen(
                viewModel = viewModel,
                onBackClick = { showSettings = false }
            )
        }

        showLog -> {
            ConversionHistoryScreen(onBackClick = { showLog = false })
        }

        else -> {
            CurrencyConverterContent(
                paddingValues = paddingValues,
                uiState = uiState,
                amountInput = amountInput,
                onAmountInputChange = { newValue ->
                    amountInput = filterAmountInput(newValue)
                },
                availableCurrencies = availableCurrencies,
                selectedFromCurrency = selectedFromCurrency,
                onFromCurrencySelected = {
                    selectedFromCurrency = it
                    viewModel.saveBaseCurrency(it)
                },
                selectedToCurrency = selectedToCurrency,
                onToCurrencySelected = {
                    selectedToCurrency = it
                    viewModel.saveLastTargetCurrency(it)
                },
                onConvertClick = {
                    handleCurrencyConversion(
                        amountInput = amountInput,
                        fromCurrency = selectedFromCurrency,
                        toCurrency = selectedToCurrency,
                        context = context,
                        viewModel = viewModel
                    ) { result, status ->
                        convertedResult = result
                        statusMessage = status
                    }
                },
                convertedResult = convertedResult,
                statusMessage = statusMessage,
                onRetryFetch = { viewModel.fetchLatestConversionRates() },
                onShowSettingsClick = { showSettings = true },
                onShowLogClick = { showLog = true }
            )
        }
    }
}

/**
 * Extracts a sorted list of available currency codes from the UI state.
 *
 * @param uiState The current UI state containing currency data.
 * @return A sorted list of currency codes or an empty list if data is not available.
 */
private fun extractAvailableCurrencies(uiState: CurrencyConverterUiState): List<String> {
    return if (uiState is CurrencyConverterUiState.Success) {
        uiState.rates.keys.sorted()
    } else {
        emptyList()
    }
}

/**
 * Filters the input string to contain only digits and at most one decimal point.
 *
 * @param input The user-entered string for the currency amount.
 * @return A sanitized version of the input suitable for parsing as a number.
 */
private fun filterAmountInput(input: String): String {
    val filteredValue = input.filter { it.isDigit() || it == '.' }
    return if (filteredValue.count { it == '.' } <= 1) filteredValue else input
}

/**
 * Handles the conversion of a currency amount from one currency to another.
 *
 * Validates the input, performs the conversion via the ViewModel,
 * formats the result, logs the conversion, and returns the result.
 *
 * @param amountInput The string representing the user-entered amount.
 * @param fromCurrency The source currency code.
 * @param toCurrency The target currency code.
 * @param context The Android context, used for logging.
 * @param viewModel The ViewModel providing the conversion logic.
 * @param onResult Callback to return the formatted result and status message.
 */
private fun handleCurrencyConversion(
    amountInput: String,
    fromCurrency: String,
    toCurrency: String,
    context: android.content.Context,
    viewModel: CurrencyConverterViewModel,
    onResult: (String, String) -> Unit
) {
    val amount = amountInput.toDoubleOrNull()
    if (isValidAmount(amount)) {
        val converted = viewModel.convertCurrency(fromCurrency, toCurrency, amount)
        if (converted != null) {
            val formattedConverted = formatAmount(converted)
            val result = "$formattedConverted $toCurrency"
            val status = "Conversion successful!"
            ConversionLogger.log(
                context = context,
                text = "$amount $fromCurrency → $formattedConverted $toCurrency"
            )
            onResult(result, status)
        } else {
            onResult("", "Conversion error: Invalid currencies or rates not available.")
        }
    } else {
        onResult("", "Please enter a valid amount.")
    }
}

/**
 * Checks whether a given amount is a valid positive number.
 *
 * @param amount The parsed amount as a Double.
 * @return True if the amount is not null and greater than 0, false otherwise.
 */
private fun isValidAmount(amount: Double?): Boolean {
    return amount != null && amount > 0
}

/**
 * Formats a currency amount to two decimal places with thousand separators.
 *
 * @param amount The amount to format.
 * @return A string representation of the formatted amount.
 */
private fun formatAmount(amount: Double): String {
    val df = DecimalFormat("#,##0.00")
    return df.format(amount)
}
