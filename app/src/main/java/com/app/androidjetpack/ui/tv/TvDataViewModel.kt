package com.app.androidjetpack.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.entity.TvEntity

class TvDataViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getTV(): LiveData<List<TvEntity>> = myRepository.getAllTv()

}