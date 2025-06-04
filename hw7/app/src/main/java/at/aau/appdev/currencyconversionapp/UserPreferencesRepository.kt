package at.aau.appdev.currencyconversionapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        private val BASE_CURRENCY_KEY = stringPreferencesKey("base_currency")
        private val LAST_TARGET_CURRENCY_KEY = stringPreferencesKey("last_target_currency")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    suspend fun saveBaseCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[BASE_CURRENCY_KEY] = currency
        }
    }

    suspend fun saveLastTargetCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_TARGET_CURRENCY_KEY] = currency
        }
    }

    suspend fun saveDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    val userPreferences: Flow<UserPreferences> = context.dataStore.data.map { preferences ->
        UserPreferences(
            baseCurrency = preferences[BASE_CURRENCY_KEY] ?: "USD",
            lastTargetCurrency = preferences[LAST_TARGET_CURRENCY_KEY] ?: "EUR",
            darkMode = preferences[DARK_MODE_KEY] == true
        )
    }
}

data class UserPreferences(
    val baseCurrency: String,
    val lastTargetCurrency: String,
    val darkMode: Boolean
)