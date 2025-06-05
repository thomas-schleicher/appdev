package at.aau.appdev.currencyconversionapp.ui.settings.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.aau.appdev.currencyconversionapp.data.model.UserPreferences
import at.aau.appdev.currencyconversionapp.ui.settings.components.common.CardTitle

/**
 * Displays a column of current user preferences in textual format.
 *
 * Includes the base currency, last used target currency, and dark mode setting.
 *
 * @param userPreferences The current [UserPreferences] to be shown in the UI.
 */
@Composable
fun CurrentPreferencesContent(userPreferences: UserPreferences) {
    Column(modifier = Modifier.padding(16.dp)) {
        CardTitle("Current Preferences")
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceText("Base Currency", userPreferences.baseCurrency)
        PreferenceText("Last Target Currency", userPreferences.lastTargetCurrency)
        PreferenceText("Dark Mode", if (userPreferences.darkMode) "Enabled" else "Disabled")
    }
}

/**
 * Displays a single user preference label-value pair in body text style.
 *
 * @param label The name of the preference (e.g., "Base Currency").
 * @param value The value of the preference (e.g., "USD").
 */
@Composable
fun PreferenceText(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodyMedium
    )
}