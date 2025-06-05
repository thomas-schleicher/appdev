package at.aau.appdev.currencyconversionapp.ui.converter

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.aau.appdev.currencyconversionapp.data.local.CurrencyRateDAO

/**
 * Factory class responsible for creating an instance of [CurrencyConverterViewModel].
 *
 * This is needed because [CurrencyConverterViewModel] has custom constructor parameters
 * (a DAO and context), which cannot be injected automatically by the default ViewModelProvider.
 *
 * @property currencyRateDao The DAO for accessing currency rate data from the local Room database.
 * @property context The application context, used within the ViewModel.
 */
class CurrencyConverterViewModelFactory(
    private val currencyRateDao: CurrencyRateDAO,
    private val context: Context
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given [modelClass], if it matches [CurrencyConverterViewModel].
     *
     * @throws IllegalArgumentException if the ViewModel class is not recognized.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyConverterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyConverterViewModel(currencyRateDao, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}