package battlemage17.study.pavelweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import battlemage17.study.pavelweatherapp.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModelWeather: WeatherViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelWeather.message.observe(viewLifecycleOwner) {
            binding.textViewWeather.text = "Weather in the selected location\n" +
                    "${it.textWeather}\n" +
                    "Temperature: ${it.tempC} °C\n" +
                    "Feels like: ${it.feelsLikeC} °C\n" +
                    "Wind speed: ${it.windKph} km/h\n" +
                    "Gust: ${it.gustKph} km/h\n" +
                    "Updated: ${it.lastUpdated}"

            Picasso.get()
                .load(it.iconWeather)
                .into(binding.imageWeatherIcon)

        }

        binding.bGetWeather.setOnClickListener {
            viewModelWeather.getResult(binding.editTextPlace.text.toString())
        }

        binding.bGetCoodinates.setOnClickListener {
            // coordinates.getLatitudeLongitude()
        }
    }

    //закрыть, если нажата стрелка Назад
    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}