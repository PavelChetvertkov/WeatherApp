package battlemage17.study.pavelweatherapp


import com.google.gson.annotations.SerializedName

data class ConditionsItem(
    @SerializedName("code")
    val code: Int, // 1000
    @SerializedName("day")
    val day: String, // Sunny
    @SerializedName("night")
    val night: String, // Clear
    @SerializedName("icon")
    val icon: Int, // 113
    @SerializedName("languages")
    val languages: List<Language>
)