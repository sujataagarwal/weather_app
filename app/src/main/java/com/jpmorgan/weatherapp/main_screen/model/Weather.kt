package com.jpmorgan.weatherapp.main_screen.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherData(
    @SerializedName("base") val base: String,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("cod") val cod: Int,
    @SerializedName("coord") val coordinate: Coordinate,
    @SerializedName("dt") val dt: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: Main,
    @SerializedName("name") val name: String,
    @SerializedName("rain") val rain: Rain,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("wind") val wind: Wind
): Serializable

data class Clouds(
    @SerializedName("all") val all: Int
): Serializable

data class Coordinate(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
): Serializable

data class Main(
    @SerializedName("feels_like") val feels_like: Double,
    @SerializedName("grnd_level") val ground_level: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("sea_level") val sea_level: Int,
    @SerializedName("temp") val temp: Double,
    @SerializedName("temp_max") val temp_max: Double,
    @SerializedName("temp_min") val temp_min: Double
): Serializable

data class Rain(
    @SerializedName("1h") val `1h`: Double
): Serializable

data class Sys(
    @SerializedName("country") val country: String,
    @SerializedName("id") val id: Int,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int,
    @SerializedName("type") val type: Int
): Serializable

data class Weather(
    @SerializedName("description") val description: String,
    @SerializedName("icon")  val icon: String,
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String
): Serializable

data class Wind(
    @SerializedName("deg") val deg: Int,
    @SerializedName("gust") val gust: Double,
    @SerializedName("speed") val speed: Double
): Serializable