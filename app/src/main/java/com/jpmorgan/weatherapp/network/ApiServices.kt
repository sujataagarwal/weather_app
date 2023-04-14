package com.jpmorgan.weatherapp.network

import com.jpmorgan.weatherapp.main_screen.model.CityData
import com.jpmorgan.weatherapp.main_screen.model.WeatherData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.io.InputStream
import java.io.OutputStream

interface ApiServices{

    // subscription validation api
    @GET("data/2.5/weather")
    suspend fun findWeather(@Query("lat") lat: Double,
                            @Query("lon") lon: Double,
                            @Query("appid") apiKey: String): Response<WeatherData>


    @GET("geo/1.0/direct")
    suspend fun findLocation(@Query("q") cityname: String,
                             @Query("appid") apiKey: String): Response<List<CityData>>

    @GET
    suspend fun findIcon(@Url s: String): Response<OutputStream>

}

