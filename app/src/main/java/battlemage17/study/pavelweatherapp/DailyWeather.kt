package battlemage17.study.pavelweatherapp

data class DailyWeather(
    var iconWeather: String,
    val textWeather: String,
    val lastUpdated: String,
    val tempC: String,
    val windKph: String,
    val feelsLikeC: String,
    val gustKph: String
)


