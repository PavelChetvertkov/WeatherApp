package battlemage17.study.pavelweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, MainFragment.newInstance())
            .commit()

        binding.bnvNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemSavedList -> {
                    //show fragment_main
                    Toast.makeText(this,"We are in this menu now", Toast.LENGTH_SHORT).show()
                    //binding.ivMainPicture.setImageResource(R.drawable.main_image_forest_hills_v2)
                }
                R.id.itemMyLocation -> {
                    //show fragment_main
                    //coordinates.getLatitudeLongitude()
                    Toast.makeText(this,"By clicking on this button, a request will be made for permission to use the user's location", Toast.LENGTH_SHORT).show()
                }
                R.id.itemHireMe -> {
                    Toast.makeText(this,"Clicking on this button will show fragment_author", Toast.LENGTH_SHORT).show()
                    /*supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeHolder, FragmentAuthor.newInstance())
                        .commit()*/
                }
            }
            true
        }
    }
}