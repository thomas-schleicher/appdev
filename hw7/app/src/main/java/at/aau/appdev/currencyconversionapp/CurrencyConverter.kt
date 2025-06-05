package at.aau.appdev.currencyconversionapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

sealed class CurrencyConverterUiState {
    data object Loading : CurrencyConverterUiState()
    data class Success(val rates: Map<String, Double>) : CurrencyConverterUiState()
    data class Error(val message: String) : CurrencyConverterUiState()
}

class CurrencyConverterViewModelFactory(
    private val currencyRateDao: CurrencyRateDAO,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyConverterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyConverterViewModel(currencyRateDao, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class CurrencyConverterViewModel(
    private val currencyRateDao: CurrencyRateDAO,
    private val context: Context
) : ViewModel() {
    private val OPEN_EXCHANGE_RATES_APP_ID = "38ea160901f248a29c1a1281f19ddcf0"

    private val userPreferencesRepository = UserPreferencesRepository(context)
    val userPreferences = userPreferencesRepository.userPreferences

    private val _uiState = MutableLiveData<CurrencyConverterUiState>()
    val uiState: LiveData<CurrencyConverterUiState> = _uiState

    private var _currentRates: Map<String, Double>? = null
    val currentRates: Map<String, Double>? get() = _currentRates

    init {
        fetchLatestConversionRates()
    }

    fun saveBaseCurrency(currency: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveBaseCurrency(currency)
        }
    }

    fun saveLastTargetCurrency(currency: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveLastTargetCurrency(currency)
        }
    }

    fun saveDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveDarkMode(isDarkMode)
        }
    }

    fun fetchLatestConversionRates() {
        viewModelScope.launch {
            _uiState.value = CurrencyConverterUiState.Loading
            if (isNetworkAvailable(context)) {
                try {
                    val response = RetrofitClient.openExchangeRatesService.getLatestRates(
                        OPEN_EXCHANGE_RATES_APP_ID
                    )
                    if (response.isSuccessful) {
                        response.body()?.let { responseBody ->
                            val rates = responseBody.rates
                            _currentRates = rates
                            _uiState.value = CurrencyConverterUiState.Success(rates)

                            val currencyRatesToInsert =
                                rates.map { (currencyAbbreviation, rateValue) ->
                                    CurrencyRate(
                                        currencyAbbreviation = currencyAbbreviation,
                                        rate = rateValue,
                                        timestamp = System.currentTimeMillis()
                                    )
                                }
                            withContext(Dispatchers.IO) {
                                currencyRateDao.insertAll(currencyRatesToInsert)
                            }
                        } ?: run {
                            _uiState.value =
                                CurrencyConverterUiState.Error("Empty response body from API. Trying to load from cache.")
                            loadCachedRates()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        _uiState.value = CurrencyConverterUiState.Error(
                            "API Error: ${response.code()} - ${response.message()} - $errorBody. Trying to load from cache."
                        )
                        loadCachedRates()
                    }
                } catch (e: Exception) {
                    _uiState.value =
                        CurrencyConverterUiState.Error("Network/Parsing Error: ${e.message}. Trying to load from cache.")
                    loadCachedRates()
                }
            } else {
                _uiState.value =
                    CurrencyConverterUiState.Error("No network available. Loading cached rates.")
                loadCachedRates()
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    private suspend fun loadCachedRates() {
        val cachedRates = withContext(Dispatchers.IO) {
            currencyRateDao.getAllRates()
        }
        if (cachedRates.isNotEmpty()) {
            val ratesMap = cachedRates.associate { it.currencyAbbreviation to it.rate }
            _currentRates = ratesMap
            _uiState.postValue(CurrencyConverterUiState.Success(ratesMap))
        } else {
            _uiState.postValue(CurrencyConverterUiState.Error("No cached rates available."))
        }
    }

    fun convertCurrency(fromCurrency: String, toCurrency: String, amount: Double): Double? {
        val rates = currentRates
        if (rates == null || fromCurrency.isEmpty() || toCurrency.isEmpty()) {
            return null
        }

        if (fromCurrency.equals(toCurrency, ignoreCase = true)) {
            return amount
        }

        val rateFromUsd: Double? = rates[fromCurrency.uppercase()]
        val rateToUsd: Double? = rates[toCurrency.uppercase()]
        if (rateFromUsd == null || rateToUsd == null || rateFromUsd == 0.0) {
            return null
        }

        val amountInUsd = amount / rateFromUsd
        return amountInUsd * rateToUsd
    }

    fun getAvailableCurrencies(): List<String> {
        return _currentRates?.keys?.sorted()?.toList() ?: emptyList()
    }
}

@Composable
fun CurrencyConverterView(viewModel: CurrencyConverterViewModel, paddingValues: PaddingValues) {
    val uiState by viewModel.uiState.observeAsState(initial = CurrencyConverterUiState.Loading)
    val userPreferences by viewModel.userPreferences.collectAsState(
        initial = UserPreferences("USD", "EUR", false)
    )

    var amountInput by remember { mutableStateOf("") }
    var selectedFromCurrency by remember { mutableStateOf("USD") }
    var selectedToCurrency by remember { mutableStateOf("EUR") }
    var convertedResult by remember { mutableStateOf<String>("") }
    var statusMessage by remember { mutableStateOf("Ready to convert") }
    var showSettings by remember { mutableStateOf(false) }
    var showLog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val availableCurrencies = when (uiState) {
        is CurrencyConverterUiState.Success -> (uiState as CurrencyConverterUiState.Success).rates.keys.sorted()
            .toList()
        else -> emptyList()
    }

    LaunchedEffect(userPreferences) {
        selectedFromCurrency = userPreferences.baseCurrency
        selectedToCurrency = userPreferences.lastTargetCurrency
    }

    when {
        showSettings -> {
            SettingsScreen(
                viewModel = viewModel,
                onBackClick = { showSettings = false }
            )
        }

        showLog -> {
            ConversionHistoryScreen(onBackClick = { showLog = false })
        }

        else -> {
            //TODO: check if the default selected currencies are available and choose alternatives if they are not
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Add Settings Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Currency Converter",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Row {
                            TextButton(onClick = { showLog = true }) {
                                Text("See Log", maxLines = 1)
                            }
                            TextButton(onClick = { showSettings = true }) {
                                Text("Settings", maxLines = 1)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = amountInput,
                        onValueChange = { newValue ->
                            val filteredValue = newValue.filter { it.isDigit() || it == '.' }
                            if (filteredValue.count { it == '.' } <= 1) {
                                amountInput = filteredValue
                            }
                        },
                        label = { Text("Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CurrencySelector(
                        label = "From Currency",
                        selectedCurrency = selectedFromCurrency,
                        currencies = availableCurrencies,
                        onCurrencySelected = {
                            selectedFromCurrency = it
                            viewModel.saveBaseCurrency(it)
                        },
                        isEnabled = uiState !is CurrencyConverterUiState.Loading && availableCurrencies.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CurrencySelector(
                        label = "To Currency",
                        selectedCurrency = selectedToCurrency,
                        currencies = availableCurrencies,
                        onCurrencySelected = {
                            selectedToCurrency = it
                            viewModel.saveLastTargetCurrency(it)
                        },
                        isEnabled = uiState !is CurrencyConverterUiState.Loading && availableCurrencies.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            val amount = amountInput.toDoubleOrNull()
                            if (amount != null && amount > 0) {
                                val converted = viewModel.convertCurrency(
                                    selectedFromCurrency,
                                    selectedToCurrency,
                                    amount
                                )
                                if (converted != null) {
                                    val df = DecimalFormat("#,##0.00")
                                    convertedResult = "${df.format(converted)} $selectedToCurrency"
                                    statusMessage = "Conversion successful!"

                                    ConversionLogger.log(
                                        context = context,
                                        text = "$amount $selectedFromCurrency → ${
                                            df.format(
                                                converted
                                            )
                                        } $selectedToCurrency"
                                    )
                                } else {
                                    convertedResult = ""
                                    statusMessage =
                                        "Conversion error: Invalid currencies or rates not available."
                                }
                            } else {
                                convertedResult = ""
                                statusMessage = "Please enter a valid amount."
                            }
                        },
                        enabled = uiState is CurrencyConverterUiState.Success && amountInput.toDoubleOrNull() != null && amountInput.toDouble() > 0,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Convert")
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    when (uiState) {
                        is CurrencyConverterUiState.Loading -> {
                            Text("Fetching rates...", modifier = Modifier.padding(top = 8.dp))
                        }

                        is CurrencyConverterUiState.Success -> {
                            Text(text = statusMessage)
                        }

                        is CurrencyConverterUiState.Error -> {
                            val errorMessage = (uiState as CurrencyConverterUiState.Error).message
                            Text(text = "Error: $errorMessage")
                            Button(onClick = { viewModel.fetchLatestConversionRates() }) {
                                Text("Retry Fetching Rates")
                            }
                        }
                    }

                    if (convertedResult.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Converted Value:",
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = convertedResult,
                                    fontSize = 32.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

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