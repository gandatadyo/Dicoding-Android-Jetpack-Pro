package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import com.app.androidjetpack.data.remote.ApiResponse
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.entity.ItemEntity
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

    override fun getDetailMovie(idmovie:String):LiveData<Resource<ItemEntity>>{
        return object : NetworkBoundResource<ItemEntity, ItemResponseEntity>(appExecutors) {
            override fun loadFromDB(): LiveData<ItemEntity> =
                localDataSource.getCourseWithModules(idmovie)

            override fun shouldFetch(courseWithModule: ItemEntity?): Boolean =
                courseWithModule?.itemId == null || courseWithModule.itemId.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ItemResponseEntity>> =
                remoteDataSource.getDetailMovie(idmovie)

            override fun saveCallResult(moduleResponses: ItemResponseEntity) {
                val moduleList = ArrayList<ItemEntity>()
                val course = ItemEntity(
                    moduleResponses.itemId,
                    moduleResponses.title,
                    moduleResponses.dateItem,
                    moduleResponses.description,
                    moduleResponses.imagePath
                )
                moduleList.add(course)
                localDataSource.insertCourses(moduleList)
            }
        }.asLiveData()
    }

    override fun getDetailTV(idtv:String):LiveData<Resource<ItemEntity>>{
        return object : NetworkBoundResource<ItemEntity, ItemResponseEntity>(appExecutors) {
            override fun loadFromDB(): LiveData<ItemEntity> =
                localDataSource.getCourseWithModules(idtv)

            override fun shouldFetch(courseWithModule: ItemEntity?): Boolean =
                courseWithModule?.itemId == null || courseWithModule.itemId.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ItemResponseEntity>> =
                remoteDataSource.getDetailTv(idtv)

            override fun saveCallResult(moduleResponses: ItemResponseEntity) {
                val moduleList = ArrayList<ItemEntity>()
                val course = ItemEntity(
                    moduleResponses.itemId,
                    moduleResponses.title,
                    moduleResponses.dateItem,
                    moduleResponses.description,
                    moduleResponses.imagePath
                )
                moduleList.add(course)
                localDataSource.insertCourses(moduleList)
            }
        }.asLiveData()
    }

    override fun getBookmarkedCourses(): LiveData<List<ItemEntity>> {
        TODO("Not yet implemented")
    }

    override fun setCourseBookmark(course: ItemEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setCourseBookmark(course, state) }

}