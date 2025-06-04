package at.aau.appdev.currencyconversionapp

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "currency_rates")
data class CurrencyRate(
    @PrimaryKey val currencyAbbreviation: String,
    val rate: Double,
    val timestamp: Long
)

@Dao
interface CurrencyRateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(currencyRate: CurrencyRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyRates: List<CurrencyRate>)

    @Query("SELECT * FROM currency_rates")
    suspend fun getAllRates(): List<CurrencyRate>

    @Query("SELECT * FROM currency_rates WHERE currencyAbbreviation = :targetCurrency")
    suspend fun getRate(targetCurrency: String): CurrencyRate?

    @Query("DELETE FROM currency_rates")
    suspend fun deleteAllRates()
}