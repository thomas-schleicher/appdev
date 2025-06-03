package at.aau.appdev.currencyconversionapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// using object ensures there is only one instance of the retrofit client
object RetrofitClient {
    private const val BASE_URL = "https://openexchangerates.org/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Courtesy of Gemini: Lazy defers the initialization of the openExchangeRatesService until it is first called.
    val openExchangeRatesService: OpenExchangeRatesService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenExchangeRatesService::class.java)
    }
}