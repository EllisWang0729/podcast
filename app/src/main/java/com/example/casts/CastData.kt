package com.example.casts

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class CastData(val data: Data) {

    data class Data(val podcast: ArrayList<PodCast>) {

        data class PodCast(
            var artistName: String? = null,
            val artworkUrl100: String? = null,
            val id: String? = null,
            val name: String? = null
        ) : Parcelable {
            constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()
            )
            override fun writeToParcel(parcel: Parcel?, p1: Int) {
                parcel?.writeString(artistName)
                parcel?.writeString(artworkUrl100)
                parcel?.writeString(id)
                parcel?.writeString(name)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<PodCast> {
                override fun createFromParcel(parcel: Parcel): PodCast {
                    return PodCast(parcel)
                }

                override fun newArray(size: Int): Array<PodCast?> {
                    return arrayOfNulls(size)
                }
            }

        }

    }
}