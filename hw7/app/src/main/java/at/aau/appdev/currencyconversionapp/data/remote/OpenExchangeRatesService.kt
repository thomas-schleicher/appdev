package at.aau.appdev.currencyconversionapp.data.remote

import at.aau.appdev.currencyconversionapp.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service interface for accessing the Open Exchange Rates API.
 */
interface OpenExchangeRatesService {
    /**
     * Fetches the latest currency exchange rates from the Open Exchange Rates API.
     *
     * Endpoint: `https://openexchangerates.org/api/latest.json`
     *
     * @param appId Your application's API key (app ID) for authentication.
     * @return A [Response] wrapping a [CurrencyResponse] object containing the latest rates.
     */
    @GET("api/latest.json")
    suspend fun getLatestRates(
        @Query("app_id") appId: String
    ): Response<CurrencyResponse>
}