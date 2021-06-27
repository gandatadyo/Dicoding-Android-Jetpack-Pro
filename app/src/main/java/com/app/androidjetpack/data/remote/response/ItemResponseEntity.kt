package com.app.androidjetpack.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemResponseEntity(
    var itemId: String,
    var title: String,
    var dateItem: String,
    var description: String,
    var imagePath: String
): Parcelable