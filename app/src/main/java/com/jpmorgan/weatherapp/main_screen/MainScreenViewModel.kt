package com.jpmorgan.weatherapp.main_screen

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jpmorgan.weatherapp.main_screen.model.CityData
import com.jpmorgan.weatherapp.main_screen.model.Weather
import com.jpmorgan.weatherapp.main_screen.model.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val locateRepo: LocateRepository): ViewModel() {
    var latitude = MutableLiveData<String>()
    var longitude = MutableLiveData<String>()
    var cityname = MutableLiveData<String>()
    var currentTemp = MutableLiveData<String>()
    var humitidy = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var feelslike = MutableLiveData<String>()


    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val mWeatherLiveData = MutableLiveData<WeatherData?>()
    var weatherLiveData: LiveData<WeatherData?>? = null
    private val mLatlongLiveData = MutableLiveData<List<CityData>?>()
    var latLongLiveData: LiveData<List<CityData>?>? = null
    private val mIconLiveData = MutableLiveData<Bitmap?>()
    var iconLiveData: LiveData<Bitmap?>? = null

    init {
        weatherLiveData = mWeatherLiveData
        latLongLiveData = mLatlongLiveData
        iconLiveData = mIconLiveData
    }
    fun findWeather() {
           try{
               coroutineScope.launch {
                   when (val result = locateRepo.findWeather(latitude.value, longitude.value)) {
                       is LocateRepository.Result.Success -> {
                           Log.e("Result Received ", ""+result.response)
                           mWeatherLiveData.postValue(result.response)
                           weatherLiveData = mWeatherLiveData
                           //findIcon(result.response.weather[0].icon)
                       }
                       is LocateRepository.Result.Failure -> {
                           mWeatherLiveData.postValue(null)
                           weatherLiveData = mWeatherLiveData
                       }
                       else -> {
                           //To Do
                       }
                   }

               }
           }
           catch (e:Exception){
                e.printStackTrace()
           }
    }

//    private fun findIcon(icon: String) {
//
//        coroutineScope.launch {
//            when( val iconResult= locateRepo.findIcon(icon)){
//                is LocateRepository.Result.SuccessImage -> {
//                    //Log.e("Result Received ", ""+iconResult.response)
//                    val inputStream = iconResult.response?.()
//                    val bitmap = BitmapFactory.decodeStream(inputStream)
////                    mWeatherLiveData.postValue(iconResult.response)
////                    weatherLiveData = mWeatherLiveData
//                }
//                is LocateRepository.Result.Failure -> {
////                    mWeatherLiveData.postValue(null)
////                    weatherLiveData = mWeatherLiveData
//                }
//                else -> {
//                    //To Do
//                }
//            }
//
//
//        }

  //  }


    fun findLocation() {
        try {
            coroutineScope.launch {
                when (val result = locateRepo.findLocation(cityname.value)) {
                    is LocateRepository.Result.SuccessCD -> {
                        Log.e("Result Received ", ""+result)
                        mLatlongLiveData.postValue(result.response)
                        latLongLiveData = mLatlongLiveData
                    }
                    is LocateRepository.Result.Failure -> {
                        mLatlongLiveData.postValue(null)
                        latLongLiveData = mLatlongLiveData
                    }
                    else -> {
                        //To Do
                    }
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }


}