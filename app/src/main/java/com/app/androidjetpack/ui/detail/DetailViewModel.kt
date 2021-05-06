package com.app.androidjetpack.ui.detail

import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.ItemEntity
import com.app.androidjetpack.utils.DataDummy

class DetailViewModel :ViewModel() {
    private lateinit var itemId: String

    fun setSelectedData(itemId: String) {
        this.itemId = itemId
    }

    fun getData(): ItemEntity? {
        var item: ItemEntity? = null
        for (itemsEntity in DataDummy.generateDummyMovie()) {
            if (itemsEntity.itemId == itemId) {
                item = itemsEntity
            }
        }
        return item
    }
}