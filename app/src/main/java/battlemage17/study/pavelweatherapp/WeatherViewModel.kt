package battlemage17.study.pavelweatherapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*

private const val API_KEY = "b8690996b95c4beb877105434221212"

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val _message = MutableLiveData<DailyWeather>()
    val message: LiveData<DailyWeather> get() = _message

    private val selectedLanguage = Locale.getDefault().language.toString()

    private lateinit var obj: JSONObject
    private var codeConditions = 0
    private var iconWeather = ""
    private var textWeather = ""
    private var lastUpdated = ""
    private var tempC = ""
    private var windKph = ""
    private var feelsLikeC = ""
    private var gustKph = ""
    private var isDay = ""

    //fun getResult(place: String): DailyWeather {
    fun getResult(place: String) {
        val url =
            "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$place&days=1&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(getApplication<Application?>().applicationContext)
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            Log.d("MyLog", "Response main: $response")
            obj = JSONObject(response)
            codeConditions =
                obj.getJSONObject("current").getJSONObject("condition").getString("code").toInt()
            iconWeather =
                obj.getJSONObject("current").getJSONObject("condition").getString("icon")
            textWeather =
                obj.getJSONObject("current").getJSONObject("condition").getString("text")
            lastUpdated = obj.getJSONObject("current").getString("last_updated")
            tempC = obj.getJSONObject("current").getString("temp_c")
            windKph = obj.getJSONObject("current").getString("wind_kph")
            feelsLikeC = obj.getJSONObject("current").getString("feelslike_c")
            gustKph = obj.getJSONObject("current").getString("gust_kph")
            isDay = obj.getJSONObject("current").getString("is_day")
        }, {
            Log.d("MyLog", "Error main: $it")
        })
        queue.add(stringRequest)
        //return DailyWeather (iconWeather, textWeather, lastUpdated, tempC, windKph, feelsLikeC, gustKph)
    }

    @GET
    fun getResultRetroFit(place: String) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$place&days=1&aqi=no&alerts=no")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getTranslationCondition() {
        val url = "https://www.weatherapi.com/docs/conditions.json"
        val queue = Volley.newRequestQueue(getApplication<Application?>().applicationContext)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                Log.d("MyLog", "Response conditions: $response")
                val conditions: Conditions =
                    Gson().fromJson(response.toString(), Conditions::class.java)

                var index1 = 0
                var index2 = 0
                var flag = false

                for (i in 0 until conditions.size) {
                    for (j in 0 until conditions[i].languages.size)
                        if ((conditions[i].languages[j].langIso == selectedLanguage) && (conditions[i].code == codeConditions)) {
                            flag = true
                            index1 = i
                            index2 = j
                            break
                        }
                    if (flag) break
                }

                if (isDay == "0") textWeather = conditions[index1].languages[index2].nightText
                if (isDay == "1") textWeather = conditions[index1].languages[index2].dayText

                _message.value = DailyWeather(
                    iconWeather,
                    textWeather,
                    lastUpdated,
                    tempC,
                    windKph,
                    feelsLikeC,
                    gustKph
                )
            },
            { error ->
                Log.d("MyLog", "Error conditions: $error")
            }
        )
        queue.add(jsonArrayRequest)
    }
}