package com.example.casts

interface MainContract {
    interface View {
        fun showCastList(podCastList: ArrayList<CastData.Data.PodCast>)
    }


    interface Presenter {
        fun getCastList()
    }

}