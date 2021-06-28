package com.app.androidjetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.vo.Resource

class DetailViewModel(private val myRepository: MyRepository):ViewModel() {
    val itemID = MutableLiveData<String>()

    fun setSelectedCourse(itemID: String) {
        this.itemID.value = itemID
    }

    fun getDetailMovies(idmovie:String): LiveData<Resource<ItemEntity>> = myRepository.getDetailMovie(idmovie)

    fun getDetailTvs(idtv:String): LiveData<Resource<ItemEntity>> = myRepository.getDetailTV(idtv)

    var itemModule: LiveData<Resource<ItemEntity>> = Transformations.switchMap(itemID) { itemID ->
        myRepository.getDetailTV(itemID)
    }

    fun setBookmark() {
        val moduleResource = itemModule.value
        if (moduleResource != null) {
            val courseWithModule = moduleResource.data
            if (courseWithModule != null) {
                val courseEntity = courseWithModule.data
                val newState = !courseEntity.bookmarked
                myRepository.setCourseBookmark(courseEntity, newState)
            }
        }
    }
}