package com.app.androidjetpack.data.source.remote

import com.app.androidjetpack.data.source.remote.response.ItemEntityResponse
import com.app.androidjetpack.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies(): List<ItemEntityResponse> = jsonHelper.loadMovies()

//    fun getModules(courseId: String): List<ItemEntityResponse> = jsonHelper.loadModule(courseId)
//    fun getContent(moduleId: String): ItemEntityResponse = jsonHelper.loadContent(moduleId)
}
