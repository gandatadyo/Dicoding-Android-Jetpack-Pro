package com.app.androidjetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.vo.Resource

class DetailViewModel(private val myRepository: MyRepository):ViewModel() {
    val itemID = MutableLiveData<String>()

    fun setSelectedItem(itemID: String) {
        this.itemID.value = itemID
    }

    // movie

    var itemMovieModule: LiveData<Resource<ItemMovieEntity>> = Transformations.switchMap(itemID) { itemID ->
        myRepository.getDetailMovie(itemID)
    }

    fun setBookmarkMovie() {
        val moduleResource = itemMovieModule.value
        if (moduleResource != null) {
            val movie = moduleResource.data
            if (movie != null) {
                val movieEntity = movie
                val newState = !movieEntity.bookmarked
                myRepository.setMovieBookmark(movieEntity, newState)
            }
        }
    }

    // tv

    var itemTvModule: LiveData<Resource<ItemTvEntity>> = Transformations.switchMap(itemID) { itemID ->
        myRepository.getDetailTV(itemID)
    }

    fun setBookmarkTv() {
        val moduleResource = itemTvModule.value
        if (moduleResource != null) {
            val tv = moduleResource.data
            if (tv != null) {
                val tvEntity = tv
                val newState = !tvEntity.bookmarked
                myRepository.setTvBookmark(tvEntity, newState)
            }
        }
    }
}