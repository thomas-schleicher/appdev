package at.aau.appdev.currencyconversionapp.ui.settings.components.cards

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.aau.appdev.currencyconversionapp.data.model.UserPreferences

/**
 * A card that displays the current user preferences, including:
 * - Base currency
 * - Last used target currency
 * - Dark mode status
 *
 * @param userPreferences The current [UserPreferences] to be displayed.
 */
@Composable
fun CurrentPreferencesCard(userPreferences: UserPreferences) {
    Card(modifier = Modifier.fillMaxWidth()) {
        CurrentPreferencesContent(userPreferences)
    }
}