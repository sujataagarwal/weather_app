package com.jpmorgan.weatherapp.main_screen

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.jpmorgan.weatherapp.main_screen.model.CityData
import com.jpmorgan.weatherapp.main_screen.model.Weather
import com.jpmorgan.weatherapp.main_screen.model.WeatherData
import com.jpmorgan.weatherapp.network.ApiServices
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LocateRepository  @Inject constructor(private var apiServices: ApiServices){

    // will be a wrapper around our response,
    sealed class Result {
        data class Success(val response: WeatherData) : Result()
        data class SuccessCD(val response: List<CityData>) : Result()
        data class  SuccessImage(val response: Bitmap) : Result();
        object Failure: Result()
    }

    suspend fun findWeather(latitude: String?, longtitude: String?): Any {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiServices.findWeather(
                    latitude?.toDouble() ?: 0.0,
                    longtitude?.toDouble() ?: 0.0, "405403b085618d43a83a9ec52c905629"
                )
                Log.e ("Response" ,  ""+ response.body())

                if (response.code()== 200 && response.body() != null) {
                    return@withContext Result.Success(response.body()!!)
                } else {
                    return@withContext Result.Failure
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }

    suspend fun findLocation(cityname: String?): Any {
        return withContext(Dispatchers.IO){
            val response = apiServices.findLocation(
                cityname?: "", "405403b085618d43a83a9ec52c905629"
            )
            Log.e ("Response1" ,  ""+ response.body())

            if (response.code()== 200 && response.body() != null) {
                return@withContext Result.SuccessCD(response.body()!!)
            } else {
                return@withContext Result.Failure
            }
        }
    }

//    suspend fun findIcon(icon: String) : Any{
//        return withContext(Dispatchers.IO){
//            val iconString = "https://openweathermap.org/img/wn/" + icon + "@2x.png"
//
//            Log.e("Response Icon", iconString)
////          //  val response = apiServices.findIcon(iconString?: ""  )
////            if (response.code()== 200 && response.body() != null) {
////                val imageStream = res
////                val bitmap = ImageIO.read(imageStream)
////                return@withContext Result.SuccessImage(bitmap)
////            } else {
////                return@withContext Result.Failure
////            }
//        }
//
//    }
}
