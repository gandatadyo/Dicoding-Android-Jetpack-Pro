package com.app.androidjetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.vo.Resource

class DetailViewModel(private val myRepository: MyRepository):ViewModel() {

    val courseId = MutableLiveData<String>()

    fun getDetailMovies(idmovie:String): LiveData<ItemEntity> = myRepository.getDetailMovie(idmovie)

    fun getDetailTvs(idtv:String): LiveData<Resource<ItemEntity>> = myRepository.getDetailTV(idtv)

    var courseModule: LiveData<Resource<ItemEntity>> = Transformations.switchMap(courseId) { mCourseId ->
        myRepository.getDetailTV(mCourseId)
    }

    fun setBookmark() {
        val moduleResource = courseModule.value
        if (moduleResource != null) {
            val courseWithModule = moduleResource.data
            if (courseWithModule != null) {
//                val courseEntity = courseWithModule.mCourse
//                val newState = !courseEntity.bookmarked
//                myRepository.setCourseBookmark(courseEntity, newState)
            }
        }
    }
}