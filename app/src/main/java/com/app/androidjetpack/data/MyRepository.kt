package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.androidjetpack.data.remote.ApiResponse
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.data.source.local.LocalDataSource
import com.app.androidjetpack.utils.AppExecutors
import com.app.androidjetpack.vo.Resource

class MyRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
    ):MyDataSource {

    companion object {
        @Volatile
        private var instance: MyRepository? = null

        fun getInstance(remoteData: RemoteDataSource,localData: LocalDataSource, appExecutors: AppExecutors): MyRepository =
            instance ?: synchronized(this) {
                MyRepository(remoteData,localData,appExecutors).apply { instance = this }
            }
    }

    override fun getAllMovie():LiveData<Resource<List<ItemEntity>>>{
        return object : NetworkBoundResource<List<ItemEntity>, List<ItemResponseEntity>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<ItemEntity>> =
                localDataSource.getAllCourses()
            override fun shouldFetch(data: List<ItemEntity>?): Boolean =
                data == null || data.isEmpty()
            public override fun createCall(): LiveData<ApiResponse<List<ItemResponseEntity>>> =
                remoteDataSource.getAllMovies()
            public override fun saveCallResult(courseResponses: List<ItemResponseEntity>) {
                val courseList = ArrayList<ItemEntity>()
                for (response in courseResponses) {
                    val course = ItemEntity(response.itemId,
                        response.title,
                        response.description,
                        response.dateItem,
                        response.imagePath)
                    courseList.add(course)
                }

                localDataSource.insertCourses(courseList)
            }
        }.asLiveData()
    }

    override fun getAllTv():LiveData<Resource<List<ItemEntity>>>{
        return object : NetworkBoundResource<List<ItemEntity>, List<ItemResponseEntity>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<ItemEntity>> =
                localDataSource.getAllCourses()
            override fun shouldFetch(data: List<ItemEntity>?): Boolean =
                data == null || data.isEmpty()
            public override fun createCall(): LiveData<ApiResponse<List<ItemResponseEntity>>> =
                remoteDataSource.getAllTv()
            public override fun saveCallResult(courseResponses: List<ItemResponseEntity>) {
                val courseList = ArrayList<ItemEntity>()
                for (response in courseResponses) {
                    val course = ItemEntity(response.itemId,
                        response.title,
                        response.description,
                        response.dateItem,
                        response.imagePath)
                    courseList.add(course)
                }

                localDataSource.insertCourses(courseList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(idmovie:String):LiveData<ItemEntity>{
        val movieResult = MutableLiveData<ItemEntity>()
        remoteDataSource.getDetailMovie(idmovie,object :RemoteDataSource.LoadDetailMovieCallback{
            override fun onDetailMovieReceived(tvsResponses: ItemResponseEntity) {
                movieResult.postValue(
                    ItemEntity(
                        tvsResponses.itemId,
                        tvsResponses.title,
                        tvsResponses.dateItem,
                        tvsResponses.description,
                        tvsResponses.imagePath,
                    )
                )
            }
        })
        return movieResult
    }

    override fun getDetailTV(idtv:String):LiveData<ItemEntity>{
        val tvResult = MutableLiveData<ItemEntity>()
        remoteDataSource.getDetailTv(idtv,object :RemoteDataSource.LoadDetailTvCallback{
            override fun onDetailTvReceived(tvsResponses: ItemResponseEntity) {
                tvResult.postValue(
                    ItemEntity(
                        tvsResponses.itemId,
                        tvsResponses.title,
                        tvsResponses.dateItem,
                        tvsResponses.description,
                        tvsResponses.imagePath,
                    )
                )
            }
        })
        return tvResult
    }

}