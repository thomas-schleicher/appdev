package at.aau.appdev.currencyconversionapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import at.aau.appdev.currencyconversionapp.data.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property to provide a singleton instance of the DataStore<Preferences>.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * Repository for accessing and updating user preferences using Jetpack DataStore.
 *
 * This class stores and retrieves the user's base currency, last-used target currency,
 * and dark mode setting in a persistent and asynchronous way.
 *
 * @param context The application context used to access the DataStore.
 */
class UserPreferencesRepository(private val context: Context) {

    companion object {
        private val BASE_CURRENCY_KEY = stringPreferencesKey("base_currency")
        private val LAST_TARGET_CURRENCY_KEY = stringPreferencesKey("last_target_currency")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    /**
     * Saves the user's preferred base currency to the DataStore.
     *
     * @param currency The currency code to save (e.g., "USD", "EUR").
     */
    suspend fun saveBaseCurrency(currency: String) {
        savePreference(BASE_CURRENCY_KEY, currency)
    }

    /**
     * Saves the last used target currency to the DataStore.
     * This is automatically updated when a conversion is performed.
     *
     * @param currency The currency code to save.
     */
    suspend fun saveLastTargetCurrency(currency: String) {
        savePreference(LAST_TARGET_CURRENCY_KEY, currency)
    }

    /**
     * Saves the user's dark mode preference.
     *
     * @param isDarkMode `true` to enable dark mode, `false` to disable.
     */
    suspend fun saveDarkMode(isDarkMode: Boolean) {
        savePreference(DARK_MODE_KEY, isDarkMode)
    }

    /**
     * A Flow that emits the current [UserPreferences] whenever preferences change.
     *
     * Default values:
     * - Base currency: "USD"
     * - Last target currency: "EUR"
     * - Dark mode: false
     */
    val userPreferences: Flow<UserPreferences> = context.dataStore.data.map { preferences ->
        UserPreferences(
            baseCurrency = preferences[BASE_CURRENCY_KEY] ?: "USD",
            lastTargetCurrency = preferences[LAST_TARGET_CURRENCY_KEY] ?: "EUR",
            darkMode = preferences[DARK_MODE_KEY] == true
        )
    }

    /**
     * Saves a single preference value to the DataStore.
     *
     * This is a generic helper function that allows writing any supported type (e.g., String, Boolean)
     * to the appropriate [Preferences.Key].
     *
     * @param key The preference key used to identify the stored value.
     * @param value The value to store under the given key.
     */
    private suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}
