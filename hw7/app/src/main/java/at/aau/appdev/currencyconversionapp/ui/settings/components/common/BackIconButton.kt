package at.aau.appdev.currencyconversionapp.ui.settings.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

/**
 * A reusable back navigation icon button.
 *
 * Displays a left-pointing arrow that calls the provided [onClick] callback
 * when pressed. Typically used in top app bars to navigate back to the previous screen.
 *
 * @param onClick The callback triggered when the back button is clicked.
 */
@Composable
fun BackIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
}