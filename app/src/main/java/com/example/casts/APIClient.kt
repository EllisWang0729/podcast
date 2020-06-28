package com.example.casts

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIClient {
    @GET("getcasts")
    fun getCasts(): Call<CastData>

    @GET("getcastdetail")
    fun getDetails(): Call<DetailData>

}