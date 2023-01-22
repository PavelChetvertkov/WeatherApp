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
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.util.*

private const val API_KEY = "b8690996b95c4beb877105434221212"

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val _message = MutableLiveData<DailyWeather>()
    val message: LiveData<DailyWeather> get() = _message

    private val selectedLanguage = Locale.getDefault().language.toString()

    //fun getResult(place: String): DailyWeather {
    fun getResult(place: String) {
        val url =
            "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$place&days=1&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(getApplication<Application?>().applicationContext)
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val obj = JSONObject(response)
            val iconWeather =
                obj.getJSONObject("current").getJSONObject("condition").getString("icon")
            val textWeather =
                obj.getJSONObject("current").getJSONObject("condition").getString("text")
            val lastUpdated = obj.getJSONObject("current").getString("last_updated")
            val tempC = obj.getJSONObject("current").getString("temp_c")
            val windKph = obj.getJSONObject("current").getString("wind_kph")
            val feelsLikeC = obj.getJSONObject("current").getString("feelslike_c")
            val gustKph = obj.getJSONObject("current").getString("gust_kph")

            _message.value = DailyWeather(
                iconWeather, textWeather, lastUpdated, tempC, windKph, feelsLikeC, gustKph
            )
            Log.d("MyLog", "Response: $response")
        }, {
            Log.d("MyLog", "Error: $it")
        })
        queue.add(stringRequest)
        //return DailyWeather (iconWeather, textWeather, lastUpdated, tempC, windKph, feelsLikeC, gustKph)
    }

    fun getResultRetroFit(place: String) {

    }

    fun getTranslationCondition(translateCondition: String) {
        val url = "https://www.weatherapi.com/docs/conditions.json"
        val queue = Volley.newRequestQueue(getApplication<Application?>().applicationContext)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                val jsonList = response.toString()
                val arrayConditionsType = object : TypeToken<Array<Conditions>>() {}.type
                //bug is here
                val conditions: Array<Conditions> = Gson().fromJson(jsonList, arrayConditionsType)
                Log.d("MyLog", "$conditions")

                Log.d("MyLog", "Response: $response")
            },
            { error ->
                Log.d("MyLog", "Error: $error")
            }
        )
        queue.add(jsonArrayRequest)
    }
}