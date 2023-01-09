package battlemage17.study.pavelweatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*

class Coordinates : AppCompatActivity() {
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    //получение разрешения
    private fun requestPermission() {
        //DEPRECATED
        /*ActivityCompat.requestPermissions(
            getApplication<Application?>().applicationContext as Activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), REQUEST_CODE
        )*/
        val requestPermissionLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // permission granted continue the normal workflow of app
                    Log.d("MyLog", "Permission granted")
                } else {
                    // if permission denied then check whether never ask
                    // again is selected or not by making use of
                    Log.i("MyLog", "permission denied")
                }
            }

        fun startLocationPermissionRequest() {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun getCityName(lat: Double, long: Double): String {
        val cityName: String
        val geoCoder =
            Geocoder(this, Locale.getDefault())
        //использую DEPRECATED, потому что альтернативный метод требует Android 13 (на нем только самые новые смартфоны)
        val address = geoCoder.getFromLocation(lat, long, 1)
        cityName = address!![0].locality
        return cityName
    }

    private fun getCountryName(lat: Double, long: Double): String {
        val countryName: String
        val geoCoder =
            Geocoder(this, Locale.getDefault())
        //использую DEPRECATED, потому что альтернативный метод требует Android 13 (на нем только самые новые смартфоны)
        val address = geoCoder.getFromLocation(lat, long, 1)
        countryName = address!![0].countryName
        return countryName
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation = p0.lastLocation
            if (lastLocation != null) {
                Toast.makeText(
                    application.applicationContext,
                    "Latitude: ${lastLocation.latitude}\n" +
                            "Longitude: ${lastLocation.longitude}\n" +
                            "Country: ${
                                getCountryName(
                                    lastLocation.latitude,
                                    lastLocation.longitude
                                )
                            }\n" +
                            "City: ${getCityName(lastLocation.latitude, lastLocation.longitude)}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //проверка включены ли службы определения геолокации
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    //проверка результата разрешения
    //DEPRECATED
    /*override fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Log.d("MyLog", "You have the permission")
    }*/

    /*private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.intervalMillis = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient!!.requestLocationUpdates(locationRequest,locationCallback,
            Looper.myLooper())
    }*/

    //проверка разрешения
    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) return true
        return false
    }

    fun getLatitudeLongitude() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        //getNewLocation()
                        Toast.makeText(
                            this,
                            "Need to create new location",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else Toast.makeText(
                        this,
                        "Latitude: ${location.latitude}\n" +
                                "Longitude: ${location.longitude}\n" +
                                "Country: ${
                                    getCountryName(
                                        location.latitude,
                                        location.longitude
                                    )
                                }\n" +
                                "City: ${getCityName(location.latitude, location.longitude)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else Toast.makeText(
                this,
                "Please, enable your location service",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            requestPermission()
            /*Toast.makeText(
                getApplication<Application?>().applicationContext,
                "Request permission",
                Toast.LENGTH_SHORT
            ).show()*/
        }
    }
}

