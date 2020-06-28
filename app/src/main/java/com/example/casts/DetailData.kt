package com.example.casts

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class DetailData(val data: Data) {
    data class Data(var collection: Collection? = null) {
        data class Collection(
            val contentFeed: ArrayList<ContentFeed?>? = null,
            val artistName: String? = null,
            val artworkUrl600: String? = null,
            val collectionName: String? = null
        ) {
            data class ContentFeed(
                val contentUrl: String? = null,
                val desc: String? = null,
                val publishedDate: String? = null,
                val title: String? = null
            ):Parcelable {
                constructor(parcel: Parcel) : this(
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString()
                ) {
                }

                override fun writeToParcel(parcel: Parcel, flags: Int) {
                    parcel.writeString(contentUrl)
                    parcel.writeString(desc)
                    parcel.writeString(publishedDate)
                    parcel.writeString(title)
                }

                override fun describeContents(): Int {
                    return 0
                }

                companion object CREATOR : Parcelable.Creator<ContentFeed> {
                    override fun createFromParcel(parcel: Parcel): ContentFeed {
                        return ContentFeed(parcel)
                    }

                    override fun newArray(size: Int): Array<ContentFeed?> {
                        return arrayOfNulls(size)
                    }
                }
            }
        }
    }
}