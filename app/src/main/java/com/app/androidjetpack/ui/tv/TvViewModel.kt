package com.app.androidjetpack.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity

class TvViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getTV(): LiveData<List<ItemEntity>> = myRepository.getAllTv()

}