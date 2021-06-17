package com.app.androidjetpack.ui.detail

import androidx.lifecycle.ViewModel

class DetailViewModel :ViewModel() {
    private lateinit var itemId: String

    fun setSelectedData(itemId: String) {
        this.itemId = itemId
    }

//    fun getData(mode:String): ItemEntity? {
//        var item: ItemEntity? = null
//        if(mode=="movie") {
//            for (itemsEntity in DataDummy.generateDummyMovie()) {
//                if (itemsEntity.itemId == itemId) {
//                    item = itemsEntity
//                }
//            }
//        }else if(mode=="tv") {
//            for (itemsEntity in DataDummy.generateDummyTvs()) {
//                if (itemsEntity.itemId == itemId) {
//                    item = itemsEntity
//                }
//            }
//        }
//        return item
//    }
}