package com.jpmorgan.weatherapp.main_screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jpmorgan.weatherapp.databinding.ActivityMainScreenBinding
import com.jpmorgan.weatherapp.utils.AppPreferences
import com.jpmorgan.weatherapp.utils.WeatherAppConstants.KELVIN
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding
    private val mainScreenViewModel by viewModels<MainScreenViewModel>()
    private val INTERNET_PERMISSION_CODE = 123
    private lateinit var appPreferences: AppPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hasPermission = hasInternetPermission(this)
        appPreferences = AppPreferences(this)

        if (hasPermission)
            requestInternetPermission(this)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.weatherModel = mainScreenViewModel

        changeVisibility(false)
        mainScreenViewModel.cityname.postValue(appPreferences.city)
        binding.btnLocate.setOnClickListener {
            appPreferences.saveCity(mainScreenViewModel.cityname.value.toString())
            mainScreenViewModel.latLongLiveData?.observe(this) {
                if (it != null && it.isNotEmpty()) {
                    mainScreenViewModel.latitude.postValue(it[0].lat.toString())
                    mainScreenViewModel.longitude.postValue(it[0].lon.toString())
                }
                else{
                    Toast.makeText(this, "City Not Found ", Toast.LENGTH_SHORT).show()
                }
            }
            mainScreenViewModel.findLocation()
        }
        binding.btnWeather.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.btnWeather.windowToken, 0)
            mainScreenViewModel.weatherLiveData?.observe(this) {
                if (it != null) {
                    changeVisibility(true)
                    mainScreenViewModel.currentTemp.postValue("Temp: " +(it.main.temp - KELVIN).roundToInt().toString() + "C")
                    mainScreenViewModel.humitidy.postValue( "Humidity: " +(it.main.humidity).toString() + "%")
                    mainScreenViewModel.feelslike.postValue( "Feels Like: " +(it.main.feels_like- KELVIN).roundToInt().toString() + "C")
                    mainScreenViewModel.description.postValue( it.weather[0].description.uppercase())

                }
                else{
                    Toast.makeText(this, "No Data Received ", Toast.LENGTH_SHORT).show()
                }
            }
            mainScreenViewModel.findWeather()

        }
    }

    private fun changeVisibility(type: Boolean) {
        if (type){
            binding.txtCurrent.visibility = View.VISIBLE
            binding.txtCurrentTemp.visibility = View.VISIBLE
            binding.txtDescription.visibility = View.VISIBLE
            binding.txtHumidity.visibility = View.VISIBLE
            binding.txtFeelslike.visibility = View.VISIBLE
            binding.cntBackground.visibility = View.VISIBLE
        }
        else{
            binding.txtCurrent.visibility = View.INVISIBLE
            binding.txtCurrentTemp.visibility = View.INVISIBLE
            binding.txtDescription.visibility = View.INVISIBLE
            binding.txtHumidity.visibility = View.INVISIBLE
            binding.txtFeelslike.visibility = View.INVISIBLE
            binding.cntBackground.visibility = View.INVISIBLE
        }

    }

    fun hasInternetPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Request internet permissions
    fun requestInternetPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.INTERNET),
            INTERNET_PERMISSION_CODE
        )
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == INTERNET_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }
}