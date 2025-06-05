package at.aau.appdev.currencyconversionapp.data.model

/**
 * Represents the user's persistent app preferences.
 *
 * These values are typically stored using Jetpack DataStore and restored when the app is reopened.
 *
 * @property baseCurrency The currency selected as the base for conversions (e.g., "EUR").
 * @property lastTargetCurrency The most recently used target currency (e.g., "USD").
 * @property darkMode Whether the user has enabled dark mode in the app UI.
 */
data class UserPreferences(
    val baseCurrency: String,
    val lastTargetCurrency: String,
    val darkMode: Boolean
)
