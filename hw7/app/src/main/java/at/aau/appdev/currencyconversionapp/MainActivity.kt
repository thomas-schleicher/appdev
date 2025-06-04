package at.aau.appdev.currencyconversionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import at.aau.appdev.currencyconversionapp.ui.theme.CurrencyConversionAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val database = AppDatabase.getDatabase(applicationContext)
        val viewModelFactory = CurrencyConverterViewModelFactory(database.currencyRateDao(), applicationContext)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CurrencyConverterViewModel = viewModel(factory = viewModelFactory)
            val userPreferences by viewModel.userPreferences.collectAsState(
                initial = UserPreferences("USD", "EUR", false)
            )

            CurrencyConversionAppTheme(darkTheme = userPreferences.darkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterView(viewModel, innerPadding)
                }
            }
        }
    }
}
