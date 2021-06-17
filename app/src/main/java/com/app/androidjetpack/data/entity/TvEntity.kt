package com.app.androidjetpack.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvEntity(
    var id: String,
    var titleTv: String,
    var dateTv: String,
    var descTv: String,
    var imgTv: String
): Parcelable