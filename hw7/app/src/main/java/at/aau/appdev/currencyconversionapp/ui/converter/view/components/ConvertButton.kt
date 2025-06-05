package at.aau.appdev.currencyconversionapp.ui.converter.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A full-width button used to trigger the currency conversion.
 *
 * This button is typically placed below the input fields and is only
 * enabled when the input is valid and conversion can be performed.
 *
 * @param enabled Indicates whether the button is enabled.
 * @param onClick Callback invoked when the user clicks the button.
 */
@Composable
fun ConvertButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Convert")
    }
}