package at.aau.appdev.currencyconversionapp.ui.settings.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import at.aau.appdev.currencyconversionapp.ui.settings.components.common.BackIconButton

/**
 * Composable that displays the top app bar for the Settings screen.
 *
 * It contains a title and a navigation icon (back button) that triggers a callback when clicked.
 *
 * @param onBackClick Callback function invoked when the back button is pressed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text("Settings") },
        navigationIcon = { BackIconButton(onClick = onBackClick) }
    )
}