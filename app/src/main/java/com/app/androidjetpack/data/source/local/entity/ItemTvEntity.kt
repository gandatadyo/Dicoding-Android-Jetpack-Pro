package com.app.androidjetpack.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tventities")
data class ItemTvEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "itemId")
    var itemId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "dateItem")
    var dateItem: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "imagePath")
    var imagePath: String,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false
)

