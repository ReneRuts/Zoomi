package com.group1.zoomi.network

import com.group1.zoomi.model.WeatherData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.open-meteo.com/"

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("v1/forecast?current_weather=true&daily=precipitation_probability_max")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("timezone") timezone: String = "auto"
    ): WeatherData
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}



@Serializable
data class WorkoutNote(
    val id: Int,
    val body: String
)

interface ZoomiPrivateApiService {
    @GET("posts/{id}")
    suspend fun getPrivateWorkoutNote(@Path("id") id: Int): WorkoutNote
}

object ZoomiPrivateApi {
    private val privateRetrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .build()

    val retrofitService: ZoomiPrivateApiService by lazy {
        privateRetrofit.create(ZoomiPrivateApiService::class.java)
    }
}
