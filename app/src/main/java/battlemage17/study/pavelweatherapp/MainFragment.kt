package battlemage17.study.pavelweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import battlemage17.study.pavelweatherapp.databinding.FragmentMainBinding
import com.bumptech.glide.Glide
import java.util.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModelWeather: WeatherViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelWeather.message.observe(viewLifecycleOwner) {
            binding.tvCurrentWeather.text =
                "${getString(R.string.weather_in_the_selected_lovation)}:\n" +
                        "${it.textWeather}\n" +
                        "${getString(R.string.feels_like)}: ${"%.0f".format(it.feelsLikeC.toFloat())} ${getString(R.string.celsius_degree)}\n" +
                        "${getString(R.string.wind_speed)}: ${"%.0f".format(it.windKph.toFloat())} ${getString(R.string.kmh)}\n" +
                        "${getString(R.string.gust)}: ${"%.0f".format(it.gustKph.toFloat())} ${getString(R.string.kmh)}\n" +
                        "${getString(R.string.last_updated)}: ${it.lastUpdated}"

            binding.tvTemperature.text =
                "%.0f".format(it.tempC.toFloat()) + " " + getString(R.string.celsius_degree)

            Glide.with(this).clear(binding.ivIconWeather)

            Glide
                .with(this)
                .load("https:${it.iconWeather}")
                .into(binding.ivIconWeather)

            binding.ivIconWeather.visibility = View.VISIBLE
        }

        binding.bGetWeather.setOnClickListener {
            viewModelWeather.getResult(binding.svEnterPlace.query.toString())
            viewModelWeather.getTranslationCondition()
            //viewModelWeather.getResultRetroFit(binding.svEnterPlace.query.toString())
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