package com.app.androidjetpack.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemEntityResponse(
    var id: String,
    var title: String,
    var description: String,
    var date: String,
    var imagePath: Int
): Parcelable
