package com.app.androidjetpack.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.response.ItemResponseEntity

class TvViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getTV(): LiveData<List<ItemResponseEntity>> = myRepository.getAllTv()

}