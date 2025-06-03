package at.aau.appdev.currencyconversionapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeRatesService {
    @GET("api/latest.json")
    suspend fun getLatestRates(
        @Query("app_id") appId: String
    ): Response<CurrencyResponse>
}