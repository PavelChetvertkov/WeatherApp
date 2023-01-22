package battlemage17.study.pavelweatherapp


import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("lang_name")
    val langName: String, // Arabic
    @SerializedName("lang_iso")
    val langIso: String, // ar
    @SerializedName("day_text")
    val dayText: String, // مشمس
    @SerializedName("night_text")
    val nightText: String // صافي
)