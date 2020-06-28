package com.example.casts

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainPresenter(private val context: Context, private val mView: MainContract.View) :
    MainContract.Presenter {

    override fun getCastList() {
        ApiManager.getCastList(object : Callback<CastData> {
            override fun onFailure(call: Call<CastData>, t: Throwable) {
                t.message?.let { Log.e(this@MainPresenter::class.java.simpleName, it) }
            }

            override fun onResponse(call: Call<CastData>, response: Response<CastData>) {
                Log.d(this@MainPresenter::class.java.simpleName, Gson().toJson(response))
                response.body()?.data?.podcast?.let {
                    mView.showCastList(it)
                }

            }
        })
    }
}