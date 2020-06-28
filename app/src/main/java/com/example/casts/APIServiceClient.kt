package com.example.casts

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIServiceClient {
    private const val BASE_URL = "http://demo4491005.mockable.io/"
    val apiClient: APIClient by lazy {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Create Retrofit client
        return@lazy retrofit.create(APIClient::class.java)
    }
}