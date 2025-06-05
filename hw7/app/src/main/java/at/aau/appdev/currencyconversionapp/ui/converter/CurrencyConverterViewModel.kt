package at.aau.appdev.currencyconversionapp.ui.converter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.aau.appdev.currencyconversionapp.data.local.CurrencyRateDAO
import at.aau.appdev.currencyconversionapp.data.local.UserPreferencesRepository
import at.aau.appdev.currencyconversionapp.data.model.CurrencyRate
import at.aau.appdev.currencyconversionapp.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.component1
import kotlin.collections.component2

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

    fun convertCurrency(fromCurrency: String, toCurrency: String, amount: Double?): Double? {
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

        val amountInUsd = amount?.div(rateFromUsd)
        return amountInUsd?.times(rateToUsd)
    }

    fun getAvailableCurrencies(): List<String> {
        return _currentRates?.keys?.sorted()?.toList() ?: emptyList()
    }
}