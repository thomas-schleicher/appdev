package at.aau.appdev.currencyconversionapp.ui.converter.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.aau.appdev.currencyconversionapp.ui.converter.CurrencyConverterUiState

/**
 * Displays the current conversion status based on the UI state.
 *
 * This section provides feedback to the user regarding the data fetching
 * and conversion process. It handles three UI states:
 * - Loading: Shows a loading message.
 * - Success: Displays a success status message.
 * - Error: Shows an error message with a retry button.
 *
 * @param uiState The current UI state from the ViewModel.
 * @param statusMessage The message to display on successful conversion.
 * @param onRetryFetch Callback invoked when the user clicks the retry button after an error.
 */
@Composable
fun ConversionStatusSection(
    uiState: CurrencyConverterUiState,
    statusMessage: String,
    onRetryFetch: () -> Unit
) {
    when (uiState) {
        is CurrencyConverterUiState.Loading -> {
            Text("Fetching rates...", modifier = Modifier.padding(top = 8.dp))
        }
        is CurrencyConverterUiState.Success -> {
            Text(text = statusMessage)
        }
        is CurrencyConverterUiState.Error -> {
            Text(text = "Error: ${(uiState as CurrencyConverterUiState.Error).message}")
            Button(onClick = onRetryFetch) {
                Text("Retry Fetching Rates")
            }
        }
    }
}
