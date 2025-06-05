package at.aau.appdev.currencyconversionapp.ui.converter.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Header section for the Currency Converter screen.
 *
 * Displays the screen title on the left and two navigation buttons
 * ("See Log" and "Settings") on the right. Intended to be placed
 * at the top of the screen layout.
 *
 * @param onShowLogClick Callback invoked when the "See Log" button is clicked.
 * @param onShowSettingsClick Callback invoked when the "Settings" button is clicked.
 */
@Composable
fun CurrencyConverterHeader(
    onShowLogClick: () -> Unit,
    onShowSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Currency Converter",
            style = MaterialTheme.typography.headlineMedium
        )
        Row {
            TextButton(onClick = onShowLogClick) {
                Text("See Log", maxLines = 1)
            }
            TextButton(onClick = onShowSettingsClick) {
                Text("Settings", maxLines = 1)
            }
        }
    }
}
