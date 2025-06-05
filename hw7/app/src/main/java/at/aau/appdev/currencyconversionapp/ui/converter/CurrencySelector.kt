package at.aau.appdev.currencyconversionapp.ui.converter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CurrencySelector(
    label: String,
    selectedCurrency: String,
    currencies: List<String>,
    onCurrencySelected: (String) -> Unit,
    isEnabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedCurrency,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        enabled = isEnabled,
        trailingIcon = {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown arrow",
                Modifier.clickable(enabled = isEnabled) { expanded = !expanded }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled) { expanded = true }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        currencies.forEach { currency ->
            DropdownMenuItem(
                text = { Text(text = currency) },
                onClick = {
                    onCurrencySelected(currency)
                    expanded = false
                }
            )
        }
    }
}