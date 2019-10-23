package com.devmina.droid_ant.getdata.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoData(
    val videoId:String,
    val title:String,
    val thumbnail:String
): Parcelable

