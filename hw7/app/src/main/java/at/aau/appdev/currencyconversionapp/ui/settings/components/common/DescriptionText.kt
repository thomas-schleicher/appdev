package at.aau.appdev.currencyconversionapp.ui.settings.components.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * A reusable composable for displaying secondary descriptive text within cards or forms.
 *
 * This text style is typically used for instructions or contextual help below a title.
 * It uses the `bodyMedium` typography style and a subdued surface variant color.
 *
 * @param text The descriptive text to display.
 */
@Composable
fun DescriptionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}