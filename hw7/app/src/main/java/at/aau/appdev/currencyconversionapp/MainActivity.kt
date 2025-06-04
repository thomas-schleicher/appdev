package at.aau.appdev.currencyconversionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import at.aau.appdev.currencyconversionapp.ui.theme.CurrencyConversionAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: CurrencyConverterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val database = AppDatabase.getDatabase(applicationContext)
        val viewModelFactory = CurrencyConverterViewModelFactory(database.currencyRateDao(), applicationContext)
        
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CurrencyConverterViewModel = viewModel(factory = viewModelFactory)
            CurrencyConversionAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterView(viewModel, innerPadding)
                }
            }
        }
    }
}
