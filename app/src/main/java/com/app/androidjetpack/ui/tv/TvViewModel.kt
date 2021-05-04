package com.app.androidjetpack.ui.tv

import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.ItemEntity
import com.app.androidjetpack.utils.DataDummy

class TvViewModel:ViewModel() {
    fun getTvs(): List<ItemEntity> = DataDummy.generateDummyTvs()
}