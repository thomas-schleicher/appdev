package at.aau.appdev.currencyconversionapp.ui.converter

/**
 * Represents the UI state of the currency converter screen.
 *
 * This sealed class helps model the different states the UI can be in,
 * such as loading data, displaying results, or showing an error.
 */
sealed class CurrencyConverterUiState {
    /**
     * UI is currently loading currency data.
     */
    data object Loading : CurrencyConverterUiState()

    /**
     * UI has successfully loaded currency data.
     *
     * @param rates A map of currency abbreviations to their corresponding exchange rates.
     */
    data class Success(val rates: Map<String, Double>) : CurrencyConverterUiState()

    /**
     * UI encountered an error while loading currency data.
     *
     * @param message A user-friendly error message to display.
     */
    data class Error(val message: String) : CurrencyConverterUiState()
}