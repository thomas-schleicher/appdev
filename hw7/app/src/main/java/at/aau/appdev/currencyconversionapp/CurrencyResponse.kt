package at.aau.appdev.currencyconversionapp

data class CurrencyResponse(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: Map<String, Double>,
    val timestamp: Int
)


