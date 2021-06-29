package com.app.androidjetpack.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemEntity

class BookmarkViewModel(private val myRepository: MyRepository): ViewModel() {
    fun getBookmarks(): LiveData<PagedList<ItemEntity>> {
        return myRepository.getBookmarkedCourses()
    }
}