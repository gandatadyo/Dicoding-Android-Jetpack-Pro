package com.app.androidjetpack.data

data class ItemEntity(
    var itemId: String,
    var title: String,
    var dateItem: String,
    var description: String,
    var bookmarked: Boolean = false,
    var imagePath: Int
)
