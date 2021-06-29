package com.app.androidjetpack.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.vo.Resource

class TvViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getTV(): LiveData<Resource<PagedList<ItemEntity>>> = myRepository.getAllTv()

}