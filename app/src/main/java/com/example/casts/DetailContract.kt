package com.example.casts

import java.util.ArrayList

interface DetailContract {
    interface View {
        fun showDetailList(dataList: ArrayList<DetailData.Data.Collection.ContentFeed?>)
        fun getImgUrl(artworkUrl600: String?)
    }

    interface Presenter {
        fun getDetail()
    }
}