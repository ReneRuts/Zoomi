package com.group1.zoomi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    @SerialName(value = "current_weather")
    val currentWeather: CurrentWeather,

    @SerialName(value = "daily")
    val daily: DailyWeather? = null
)

@Serializable
data class CurrentWeather(
    val temperature: Double,
    val windspeed: Double,
    val time: String
)

@Serializable
data class DailyWeather(
    val time: List<String>,
    @SerialName("precipitation_probability_max")
    val precipitationProbabilityMax: List<Int>
)

