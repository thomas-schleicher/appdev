package at.aau.appdev.currencyconversionapp.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton object that provides a configured Retrofit client
 * for accessing the Open Exchange Rates API.
 *
 * Using `object` ensures that only a single instance of the client exists throughout the app.
 */
object RetrofitClient {
    /**
     * Base URL of the Open Exchange Rates API.
     */
    private const val BASE_URL = "https://openexchangerates.org/"

    /**
     * Custom [OkHttpClient] with connection, read, and write timeouts set to 30 seconds.
     * This allows the app to handle slower network conditions more gracefully.
     */
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Lazily-initialized Retrofit service interface to interact with the API.
     *
     * The initialization is deferred until it is accessed for the first time (`by lazy`),
     * which helps optimize resource usage.
     *
     * Uses:
     * - `BASE_URL` as the base endpoint
     * - [okHttpClient] for network configuration
     * - `GsonConverterFactory` to parse JSON responses into Kotlin data classes
     */
    val openExchangeRatesService: OpenExchangeRatesService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenExchangeRatesService::class.java)
    }
}