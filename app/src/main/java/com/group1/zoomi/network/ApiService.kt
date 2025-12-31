package com.group1.zoomi.network

import com.group1.zoomi.model.WeatherData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
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
data class Feedback(
    val id: Int,
    val body: String
)

interface FeedbackApiService {
    @Headers("Accept: application/vnd.pgrst.object+json")
    @GET("feedback")
    suspend fun getFeedback(@Query("id") id: String): Feedback
}

object FeedbackApi {
    private const val API_KEY = "sb_secret_156kKmZuZzwRbsdIT9Nsqg_6iPfWGKk"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("apikey", API_KEY)
                .header("Authorization", "Bearer $API_KEY")
                .build()
            chain.proceed(request)
        }.build()

    private val privateRetrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://cbtzemxevdlvfhcwozgx.supabase.co/rest/v1/")
        .client(okHttpClient)
        .build()


    val retrofitService: FeedbackApiService by lazy {
        privateRetrofit.create(FeedbackApiService::class.java)
    }
}
