package battlemage17.study.pavelweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import battlemage17.study.pavelweatherapp.databinding.FragmentMainBinding

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
                        "${getString(R.string.temperature)}: ${it.tempC} ${getString(R.string.celsius_degree)}\n" +
                        "${getString(R.string.feels_like)}: ${it.feelsLikeC} ${getString(R.string.celsius_degree)}\n" +
                        "${getString(R.string.wind_speed)}: ${it.windKph} ${getString(R.string.kmh)}\n" +
                        "${getString(R.string.gust)}: ${it.gustKph} ${getString(R.string.kmh)}\n" +
                        "${getString(R.string.last_updated)}: ${it.lastUpdated}"
            when (it.textWeather) {
                //потом засунуть все это в строки (untranslatable)
                "Clear" -> binding.ivMainPicture.setImageResource(R.drawable.sunset_clear_sky)
                "Overcast" -> binding.ivMainPicture.setImageResource(R.drawable.sunset_clouds)
                else -> binding.ivMainPicture.setImageResource(R.drawable.night_snowfall)
            }
        }

        binding.bGetWeather.setOnClickListener {
            viewModelWeather.getResult(binding.editTextPlace.text.toString())
        }
        binding.bnvNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemSavedList -> {
                    //Toast.makeText(this,"Здесь будет список городов", Toast.LENGTH_SHORT).show()
                }
                R.id.itemMyLocation -> {
                    //Toast.makeText(this,"По нажатию на эту кнопку будет запрос на разрешение использования локации пользователя", Toast.LENGTH_SHORT).show()
                }
                R.id.itemHireMe -> {
                    /*supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeHolder, FragmentAuthor.newInstance())
                        .commit()*/
                }
            }
            true
        }

        /*binding.bGetCoordinates.setOnClickListener {
             // coordinates.getLatitudeLongitude()
         }*/
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