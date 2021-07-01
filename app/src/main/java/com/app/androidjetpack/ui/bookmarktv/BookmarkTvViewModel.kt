package com.app.androidjetpack.ui.bookmarktv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity

class BookmarkTvViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getBookmarks(filter: String): LiveData<PagedList<ItemTvEntity>> {
        return myRepository.getBookmarkedTvs(filter)
    }

}