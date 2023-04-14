package com.jpmorgan.weatherapp.main_screen.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CityData(
    @SerializedName("country") val country: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("local_names") val local_names: LocalNames,
    @SerializedName("lon") val lon: Double,
    @SerializedName("name") val name: String
):Serializable

data class LocalNames(
    @SerializedName("ascii") val ascii: String,
    @SerializedName("feature_name") val feature_name: String,
): Serializable