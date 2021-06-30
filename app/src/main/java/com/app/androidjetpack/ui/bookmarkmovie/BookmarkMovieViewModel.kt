package com.app.androidjetpack.ui.bookmarkmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity

class BookmarkMovieViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getBookmarks(filter: String): LiveData<PagedList<ItemMovieEntity>> {
        return myRepository.getBookmarkedMovies(filter)
    }

}