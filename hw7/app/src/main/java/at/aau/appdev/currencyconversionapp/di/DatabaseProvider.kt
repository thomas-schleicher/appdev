package at.aau.appdev.currencyconversionapp.di

import android.content.Context
import at.aau.appdev.currencyconversionapp.data.local.AppDatabase
import at.aau.appdev.currencyconversionapp.data.local.CurrencyRateDAO

/**
 * A utility object responsible for providing instances related to the local Room database.
 *
 * This class serves as a simple manual dependency injector for accessing the
 * [AppDatabase] and its [CurrencyRateDAO]. It helps decouple database creation logic
 * from the rest of the app, improving modularity and testability.
 */
object DatabaseProvider {
    /**
     * Provides a singleton instance of the Room database.
     *
     * @param context The application context used to initialize the database.
     * @return A singleton [AppDatabase] instance.
     */
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    /**
     * Provides an instance of [CurrencyRateDAO] to perform database operations
     * related to currency exchange rates.
     *
     * @param context The application context used to access the database.
     * @return An instance of [CurrencyRateDAO].
     */
    fun provideCurrencyRateDao(context: Context): CurrencyRateDAO {
        return provideDatabase(context).currencyRateDao()
    }
}