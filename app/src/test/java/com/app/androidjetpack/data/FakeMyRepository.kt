package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.androidjetpack.data.remote.ApiResponse
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.LocalDataSource
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.utils.AppExecutors
import com.app.androidjetpack.vo.Resource

class FakeMyRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MyDataSource {

    override fun getAllMovie(): LiveData<Resource<PagedList<ItemMovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ItemMovieEntity>, List<ItemResponseEntity>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<ItemMovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<ItemMovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<ItemResponseEntity>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(courseResponses: List<ItemResponseEntity>) {
                val courseList = ArrayList<ItemMovieEntity>()
                for (response in courseResponses) {
                    val course = ItemMovieEntity(
                        response.itemId,
                        response.title,
                        response.description,
                        response.dateItem,
                        response.imagePath
                    )
                    courseList.add(course)
                }

                localDataSource.insertMovies(courseList)
            }
        }.asLiveData()
    }

    override fun getAllTv(): LiveData<Resource<PagedList<ItemTvEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ItemTvEntity>, List<ItemResponseEntity>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<ItemTvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvs(), config).build()
            }

            override fun shouldFetch(data: PagedList<ItemTvEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<ItemResponseEntity>>> =
                remoteDataSource.getAllTv()

            public override fun saveCallResult(courseResponses: List<ItemResponseEntity>) {
                val courseList = ArrayList<ItemTvEntity>()
                for (response in courseResponses) {
                    val course = ItemTvEntity(
                        response.itemId,
                        response.title,
                        response.description,
                        response.dateItem,
                        response.imagePath
                    )
                    courseList.add(course)
                }

                localDataSource.insertTvs(courseList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(idmovie: String): LiveData<Resource<ItemMovieEntity>> {
        return object : NetworkBoundResource<ItemMovieEntity, ItemResponseEntity>(appExecutors) {
            override fun loadFromDB(): LiveData<ItemMovieEntity> =
                localDataSource.getMovieDetail(idmovie)

            override fun shouldFetch(courseWithModule: ItemMovieEntity?): Boolean =
                courseWithModule?.itemId == null || courseWithModule.itemId.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ItemResponseEntity>> =
                remoteDataSource.getDetailMovie(idmovie)

            override fun saveCallResult(moduleResponses: ItemResponseEntity) {
                val moduleList = ArrayList<ItemMovieEntity>()
                val course = ItemMovieEntity(
                    moduleResponses.itemId,
                    moduleResponses.title,
                    moduleResponses.dateItem,
                    moduleResponses.description,
                    moduleResponses.imagePath
                )
                moduleList.add(course)
                localDataSource.insertMovies(moduleList)
            }
        }.asLiveData()
    }

    override fun getDetailTV(idtv: String): LiveData<Resource<ItemTvEntity>> {
        return object : NetworkBoundResource<ItemTvEntity, ItemResponseEntity>(appExecutors) {
            override fun loadFromDB(): LiveData<ItemTvEntity> =
                localDataSource.getTvDetail(idtv)

            override fun shouldFetch(courseWithModule: ItemTvEntity?): Boolean =
                courseWithModule?.itemId == null || courseWithModule.itemId.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ItemResponseEntity>> =
                remoteDataSource.getDetailTv(idtv)

            override fun saveCallResult(moduleResponses: ItemResponseEntity) {
                val moduleList = ArrayList<ItemTvEntity>()
                val course = ItemTvEntity(
                    moduleResponses.itemId,
                    moduleResponses.title,
                    moduleResponses.dateItem,
                    moduleResponses.description,
                    moduleResponses.imagePath
                )
                moduleList.add(course)
                localDataSource.insertTvs(moduleList)
            }
        }.asLiveData()
    }

    override fun getBookmarkedMovies(filter: String): LiveData<PagedList<ItemMovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getBookmarkedMovies(filter), config).build()
    }

    override fun setMovieBookmark(course: ItemMovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMovieBookmark(course, state) }

    override fun getBookmarkedTvs(filter: String): LiveData<PagedList<ItemTvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getBookmarkedTvs(filter), config).build()
    }

    override fun setTvBookmark(course: ItemTvEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setTvBookmark(course, state) }

}