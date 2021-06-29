package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.vo.Resource

interface MyDataSource {

    fun getAllMovie(): LiveData<Resource<PagedList<ItemEntity>>>

    fun getAllTv(): LiveData<Resource<PagedList<ItemEntity>>>

    fun getDetailMovie(idmovie:String): LiveData<Resource<ItemEntity>>

    fun getDetailTV(idtv:String): LiveData<Resource<ItemEntity>>

    fun getBookmarkedCourses(): LiveData<PagedList<ItemEntity>>

    fun setCourseBookmark(course: ItemEntity, state: Boolean)
}