package battlemage17.study.pavelweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import battlemage17.study.pavelweatherapp.databinding.FragmentMainBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.util.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModelWeather: WeatherViewModel by activityViewModels()
    private val selectedLanguage = Locale.getDefault().language.toString()

    private fun translateCondition() {
        val urlCondition =
            "https://www.weatherapi.com/docs/conditions.json"
        val queueCondition = Volley.newRequestQueue(requireActivity())
        val stringRequestCondition = StringRequest(
            Request.Method.GET,
            urlCondition,
            { response ->
                //val obj =
                    JSONObject(response)

                //val textWeather = obj.getJSONObject("languages").getString("lang_iso")
                //if (selectedLanguage == textWeather)

                Toast.makeText(requireActivity(), "Response: $response", Toast.LENGTH_SHORT).show()
                Log.d("MyLog", "Response: $response")
            },
            {
                Toast.makeText(requireActivity(), "Error: $it", Toast.LENGTH_SHORT).show()
                Log.d("MyLog", "Error: $it")
            }
        )
        queueCondition.add(stringRequestCondition)
    }

    private fun translateConditionRetroFit() {

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelWeather.message.observe(viewLifecycleOwner) {
            binding.tvCurrentWeather.text =
                "${getString(R.string.weather_in_the_selected_lovation)}:\n" +
                        "${it.textWeather}\n" +
                        "${getString(R.string.temperature)}: ${it.tempC} ${getString(R.string.celsius_degree)}\n" +
                        "${getString(R.string.feels_like)}: ${it.feelsLikeC} ${getString(R.string.celsius_degree)}\n" +
                        "${getString(R.string.wind_speed)}: ${it.windKph} ${getString(R.string.kmh)}\n" +
                        "${getString(R.string.gust)}: ${it.gustKph} ${getString(R.string.kmh)}\n" +
                        "${getString(R.string.last_updated)}: ${it.lastUpdated}"

            //translateCondition()

            Glide.with(this).clear(binding.ivIconWeather)

            Glide
                .with(this)
                .load("https:${it.iconWeather}")
                .into(binding.ivIconWeather)

            binding.ivIconWeather.visibility = View.VISIBLE
        }

        binding.bGetWeather.setOnClickListener {
            viewModelWeather.getResult(binding.svEnterPlace.query.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}