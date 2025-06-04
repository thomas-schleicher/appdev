package at.aau.appdev.currencyconversionapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: CurrencyConverterViewModel,
    onBackClick: () -> Unit
) {
    val userPreferences by viewModel.userPreferences.collectAsState(
        initial = UserPreferences("USD", "EUR", false)
    )
    val availableCurrencies = viewModel.getAvailableCurrencies()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Base Currency",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your preferred base currency for conversions",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (availableCurrencies.isNotEmpty()) {
                        CurrencySelector(
                            label = "Select Base Currency",
                            selectedCurrency = userPreferences.baseCurrency,
                            currencies = availableCurrencies,
                            onCurrencySelected = { currency ->
                                viewModel.saveBaseCurrency(currency)
                            },
                            isEnabled = true
                        )
                    } else {
                        Text(
                            text = "Loading currencies...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Dark Mode",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Toggle dark theme",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = userPreferences.darkMode,
                        onCheckedChange = { isChecked ->
                            viewModel.saveDarkMode(isChecked)
                        }
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Current Preferences",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Base Currency: ${userPreferences.baseCurrency}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Last Target Currency: ${userPreferences.lastTargetCurrency}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Dark Mode: ${if (userPreferences.darkMode) "Enabled" else "Disabled"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}