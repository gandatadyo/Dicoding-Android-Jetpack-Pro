package com.app.androidjetpack.data

import com.app.androidjetpack.R
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.data.source.remote.RemoteDataSource
import java.util.ArrayList

class AcademyRepository private constructor(private val remoteDataSource: RemoteDataSource) : AcademyDataSource {

    companion object {
        @Volatile
        private var instance: AcademyRepository? = null

        fun getInstance(remoteData: RemoteDataSource): AcademyRepository =
            instance ?: synchronized(this) {
                AcademyRepository(remoteData).apply { instance = this }
            }
    }

    override fun getAllMovies(): ArrayList<ItemEntity> {
        val courseResponses = remoteDataSource.getAllMovies()
        val courseList = ArrayList<ItemEntity>()
        for (response in courseResponses) {
            val course = ItemEntity(response.id,
                response.title,
                response.description,
                response.date,
                R.drawable.poster_arrow)
            courseList.add(course)
        }
        return courseList
    }
}