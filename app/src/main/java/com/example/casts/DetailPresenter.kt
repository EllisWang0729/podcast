package com.example.casts

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(private val context: Context, private val mView: DetailContract.View) : DetailContract.Presenter {
    override fun getDetail() {
        ApiManager.getCastDetailList(object : Callback<DetailData> {
            override fun onFailure(call: Call<DetailData>, t: Throwable) {
                t.message?.let { Log.e(this@DetailPresenter::class.java.simpleName, it) }
            }

            override fun onResponse(call: Call<DetailData>, response: Response<DetailData>) {
                Log.d(this@DetailPresenter::class.java.simpleName, Gson().toJson(response))
                response.body()?.data?.collection?.artworkUrl600?.let {
                    mView.getImgUrl(it)
                }
                response.body()?.data?.collection?.contentFeed?.let {
                    mView.showDetailList(it)
                }
            }
        })
    }
}