package battlemage17.study.pavelweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import battlemage17.study.pavelweatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val coordinates  = Coordinates()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnvNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemSavedList -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeHolder, MainFragment.newInstance())
                        .commit()
                }
                R.id.itemMyLocation -> {
                    //coordinates.getLatitudeLongitude()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeHolder, FragmentMyLocation.newInstance())
                        .commit()
                }
                R.id.itemHireMe -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeHolder, FragmentHireMe.newInstance())
                        .commit()
                }
            }
            true
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, MainFragment.newInstance())
            .commit()
    }


}