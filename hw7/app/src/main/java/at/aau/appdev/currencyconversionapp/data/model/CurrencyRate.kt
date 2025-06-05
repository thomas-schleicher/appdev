package at.aau.appdev.currencyconversionapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Defines a Room Entity representing a row in the 'currency_rates' table.
 */
@Entity(tableName = "currency_rates")
data class CurrencyRate(
    /**
     * Primary key: the currency abbreviation (e.g., "USD", "EUR").
     * Each currency must be unique in the table.
     */
    @PrimaryKey val currencyAbbreviation: String,

    /**
     * The exchange rate relative to the base currency.
     * Example: 1.08 means 1 USD = 1.08 EUR if base is USD.
     */
    val rate: Double,

    /**
     * Timestamp of when the rate was last updated or fetched.
     * Useful for cache freshness or "last updated" display.
     */
    val timestamp: Long
)