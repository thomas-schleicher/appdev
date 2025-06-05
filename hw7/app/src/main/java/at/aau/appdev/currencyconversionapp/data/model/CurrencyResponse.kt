package at.aau.appdev.currencyconversionapp.data.model

/**
 * Data model representing the JSON response from the Open Exchange Rates API.
 *
 * This class is used by Retrofit and Gson to deserialize the API response for the latest exchange rates.
 *
 * Example endpoint: `https://openexchangerates.org/api/latest.json`
 *
 * @property base The base currency for all conversion rates (usually "USD").
 * @property disclaimer Legal disclaimer required by the API provider.
 * @property license License information for the API usage.
 * @property rates A map of currency abbreviations to their exchange rates relative to the base.
 * @property timestamp Unix timestamp indicating when the rates were last updated.
 */
data class CurrencyResponse(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: Map<String, Double>,
    val timestamp: Int
)