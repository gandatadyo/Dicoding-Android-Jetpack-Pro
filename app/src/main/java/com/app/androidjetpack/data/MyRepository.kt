package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity

class MyRepository private constructor(private val remoteDataSource: RemoteDataSource):MyDataSource {

    companion object {
        @Volatile
        private var instance: MyRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MyRepository =
            instance ?: synchronized(this) {
                MyRepository(remoteData).apply { instance = this }
            }
    }

    override fun getAllMovie():LiveData<List<ItemEntity>> {
        val movieResult = MutableLiveData<List<ItemEntity>>()
        remoteDataSource.getAllMovies(object :RemoteDataSource.LoadAllMovieCallback{
            override fun onAllMoviesReceived(moviesResponses: List<ItemResponseEntity>) {
                val movieList = ArrayList<ItemEntity>()
                for(response in moviesResponses){
                    movieList.add(
                        ItemEntity(
                            response.itemId,
                            response.title,
                            response.dateItem,
                            response.description,
                            0,
                        )
                    )
                }
                movieResult.postValue(movieList)
            }

        })
        return movieResult
    }

    override fun getDetailMovie(idmovie:String):LiveData<ItemResponseEntity>{
        val movieResult = MutableLiveData<ItemResponseEntity>()
        remoteDataSource.getDetailMovie(idmovie,object :RemoteDataSource.LoadDetailMovieCallback{
            override fun onDetailMovieReceived(tvsResponses: ItemResponseEntity) {
                movieResult.postValue(tvsResponses)
            }
        })
        return movieResult
    }

    override fun getAllTv():LiveData<List<ItemEntity>>{
        val tvResult = MutableLiveData<List<ItemEntity>>()
        remoteDataSource.getAllTv(object :RemoteDataSource.LoadAllTvCallback{
            override fun onAllTvsReceived(tvsResponses: List<ItemResponseEntity>) {
                val tvlist = ArrayList<ItemEntity>()
                for(response in tvsResponses){
                    tvlist.add(
                        ItemEntity(
                            response.itemId,
                            response.title,
                            response.dateItem,
                            response.description,
                            0,
                        )
                    )
                }
                tvResult.postValue(tvlist)
            }
        })
        return tvResult
    }

    override fun getDetailTV(idtv:String):LiveData<ItemResponseEntity>{
        val tvResult = MutableLiveData<ItemResponseEntity>()
        remoteDataSource.getDetailTv(idtv,object :RemoteDataSource.LoadDetailTvCallback{
            override fun onDetailTvReceived(tvsResponses: ItemResponseEntity) {
                tvResult.postValue(tvsResponses)
            }
        })
        return tvResult
    }

}