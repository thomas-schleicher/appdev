package at.aau.appdev.currencyconversionapp.ui.settings.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.aau.appdev.currencyconversionapp.ui.settings.components.common.CardTitle
import at.aau.appdev.currencyconversionapp.ui.settings.components.common.DescriptionText

/**
 * Content layout for the Dark Mode settings card.
 *
 * Displays a row with a label and description on the left and a toggle switch on the right.
 *
 * @param darkModeEnabled Whether dark mode is currently enabled.
 * @param onToggleDarkMode Callback triggered when the switch state changes.
 */
@Composable
fun DarkModeCardContent(
    darkModeEnabled: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DarkModeInfoColumn(modifier = Modifier.weight(1f))
        DarkModeSwitch(
            checked = darkModeEnabled,
            onCheckedChange = onToggleDarkMode
        )
    }
}

/**
 * A column containing the title and description for the dark mode setting.
 *
 * @param modifier Optional [Modifier] to customize the layout.
 */
@Composable
fun DarkModeInfoColumn(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        CardTitle("Dark Mode")
        DescriptionText("Toggle dark theme")
    }
}

/**
 * A switch component used to toggle the dark mode setting on or off.
 *
 * @param checked Whether the switch is currently checked (enabled).
 * @param onCheckedChange Callback triggered when the checked state changes.
 */
@Composable
fun DarkModeSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}