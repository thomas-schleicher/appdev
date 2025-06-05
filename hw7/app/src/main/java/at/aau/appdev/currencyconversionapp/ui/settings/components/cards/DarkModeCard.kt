package at.aau.appdev.currencyconversionapp.ui.settings.components.cards

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A card component that allows the user to toggle dark mode on or off.
 *
 * This wraps [DarkModeCardContent] in a full-width [Card], providing a consistent layout
 * for the appearance of user preference settings.
 *
 * @param darkModeEnabled Whether dark mode is currently enabled.
 * @param onToggleDarkMode Callback triggered when the user toggles the switch.
 */
@Composable
fun DarkModeCard(
    darkModeEnabled: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        DarkModeCardContent(
            darkModeEnabled = darkModeEnabled,
            onToggleDarkMode = onToggleDarkMode
        )
    }
}