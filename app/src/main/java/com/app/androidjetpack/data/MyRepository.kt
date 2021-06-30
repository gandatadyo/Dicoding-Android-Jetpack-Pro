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

    // movie

    override fun getAllMovie():LiveData<Resource<PagedList<ItemMovieEntity>>>{
        return object : NetworkBoundResource<PagedList<ItemMovieEntity>, List<ItemResponseEntity>>(appExecutors) {
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
            public override fun saveCallResult(itemResponses: List<ItemResponseEntity>) {
                val itemList = ArrayList<ItemMovieEntity>()
                for (response in itemResponses) {
                    val item = ItemMovieEntity(response.itemId,
                        response.title,
                        response.description,
                        response.dateItem,
                        response.imagePath)
                    itemList.add(item)
                }

                localDataSource.insertMovies(itemList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(idmovie:String):LiveData<Resource<ItemMovieEntity>>{
        return object : NetworkBoundResource<ItemMovieEntity, ItemResponseEntity>(appExecutors) {
            override fun loadFromDB(): LiveData<ItemMovieEntity> =
                localDataSource.getMovieDetail(idmovie)

            override fun shouldFetch(movie: ItemMovieEntity?): Boolean =
                movie?.itemId == null || movie.itemId.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ItemResponseEntity>> =
                remoteDataSource.getDetailMovie(idmovie)

            override fun saveCallResult(moduleResponses: ItemResponseEntity) {
                val moduleList = ArrayList<ItemMovieEntity>()
                val movie = ItemMovieEntity(
                    moduleResponses.itemId,
                    moduleResponses.title,
                    moduleResponses.dateItem,
                    moduleResponses.description,
                    moduleResponses.imagePath
                )
                moduleList.add(movie)
                localDataSource.insertMovies(moduleList)
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

    override fun setMovieBookmark(movie: ItemMovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMovieBookmark(movie, state) }

    // tv

    override fun getAllTv():LiveData<Resource<PagedList<ItemTvEntity>>>{
        return object : NetworkBoundResource<PagedList<ItemTvEntity>, List<ItemResponseEntity>>(appExecutors) {
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
            public override fun saveCallResult(itemResponses: List<ItemResponseEntity>) {
                val itemList = ArrayList<ItemTvEntity>()
                for (response in itemResponses) {
                    val item = ItemTvEntity(response.itemId,
                        response.title,
                        response.description,
                        response.dateItem,
                        response.imagePath)
                    itemList.add(item)
                }

                localDataSource.insertTvs(itemList)
            }
        }.asLiveData()
    }

    override fun getDetailTV(idtv:String):LiveData<Resource<ItemTvEntity>>{
        return object : NetworkBoundResource<ItemTvEntity, ItemResponseEntity>(appExecutors) {
            override fun loadFromDB(): LiveData<ItemTvEntity> =
                localDataSource.getTvDetail(idtv)

            override fun shouldFetch(tv: ItemTvEntity?): Boolean =
                tv?.itemId == null || tv.itemId.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ItemResponseEntity>> =
                remoteDataSource.getDetailTv(idtv)

            override fun saveCallResult(moduleResponses: ItemResponseEntity) {
                val moduleList = ArrayList<ItemTvEntity>()
                val tv = ItemTvEntity(
                    moduleResponses.itemId,
                    moduleResponses.title,
                    moduleResponses.dateItem,
                    moduleResponses.description,
                    moduleResponses.imagePath
                )
                moduleList.add(tv)
                localDataSource.insertTvs(moduleList)
            }
        }.asLiveData()
    }

    override fun getBookmarkedTvs(filter: String): LiveData<PagedList<ItemTvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getBookmarkedTvs(filter), config).build()
    }

    override fun setTvBookmark(tv: ItemTvEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setTvBookmark(tv, state) }

}