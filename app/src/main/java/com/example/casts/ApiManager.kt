package com.example.casts

import retrofit2.Callback

object ApiManager {

    fun getCastList(callback: Callback<CastData>) {
        APIServiceClient.apiClient.getCasts().enqueue(callback)
    }

    fun getCastDetailList(callback: Callback<DetailData>) {
        APIServiceClient.apiClient.getDetails().enqueue(callback)
    }
}


