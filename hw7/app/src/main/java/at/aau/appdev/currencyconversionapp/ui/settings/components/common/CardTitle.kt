package at.aau.appdev.currencyconversionapp.ui.settings.components.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * A reusable composable for rendering a title inside a Card layout.
 *
 * This text is styled using `titleMedium` from the current Material theme,
 * making it suitable for labeling sections or setting headings in cards or dialogs.
 *
 * @param text The title text to be displayed.
 */
@Composable
fun CardTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium
    )
}