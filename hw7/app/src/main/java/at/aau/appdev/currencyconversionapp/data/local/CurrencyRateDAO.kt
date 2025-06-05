package at.aau.appdev.currencyconversionapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import at.aau.appdev.currencyconversionapp.data.model.CurrencyRate

/**
 * Data Access Object (DAO) for performing database operations on [CurrencyRate] entities.
 * Room generates the implementation of this interface at compile time.
 */
@Dao
interface CurrencyRateDAO {
    /**
     * Inserts a single [CurrencyRate] into the database.
     * If a record with the same [currencyAbbreviation][CurrencyRate.currencyAbbreviation] already exists,
     * it will be replaced.
     *
     * @param currencyRate The [CurrencyRate] to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(currencyRate: CurrencyRate)

    /**
     * Inserts a list of [CurrencyRate] entries into the database.
     * Existing entries with the same primary key will be replaced.
     *
     * @param currencyRates The list of rates to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyRates: List<CurrencyRate>)

    /**
     * Retrieves all currency rates currently stored in the database.
     *
     * @return A list of all [CurrencyRate] records.
     */
    @Query("SELECT * FROM currency_rates")
    suspend fun getAllRates(): List<CurrencyRate>

    /**
     * Retrieves the rate for a specific currency, identified by its abbreviation.
     *
     * @param targetCurrency The currency code (e.g., "USD", "EUR").
     * @return The [CurrencyRate] if found, or `null` if no match exists.
     */
    @Query("SELECT * FROM currency_rates WHERE currencyAbbreviation = :targetCurrency")
    suspend fun getRate(targetCurrency: String): CurrencyRate?

    /**
     * Deletes all currency rates from the database.
     */
    @Query("DELETE FROM currency_rates")
    suspend fun deleteAllRates()
}