package com.app.androidjetpack.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieEntity(
    var id: String,
    var titleMovie: String,
    var dateMovie: String,
    var descMovie: String,
    var imgMovie: String
): Parcelable