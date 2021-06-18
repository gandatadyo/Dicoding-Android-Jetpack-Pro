package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.data.entity.TvEntity
import com.app.androidjetpack.data.remote.RemoteDataSource

class MyRepository private constructor(private val remoteDataSource: RemoteDataSource):MyDataSource {

    companion object {
        @Volatile
        private var instance: MyRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MyRepository =
            instance ?: synchronized(this) {
                MyRepository(remoteData).apply { instance = this }
            }
    }

    override fun getAllMovie():LiveData<List<MovieEntity>> {
        val movieResult = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getAllMovies(object :RemoteDataSource.LoadAllMovieCallback{
            override fun onAllMoviesReceived(moviesResponses: List<MovieEntity>) {
                val movieList = ArrayList<MovieEntity>()
                for(response in moviesResponses){
                    movieList.add(
                        MovieEntity(
                            response.id,
                            response.titleMovie,
                            response.dateMovie,
                            response.descMovie,
                            response.imgMovie,
                        )
                    )
                }
                movieResult.postValue(movieList)
            }

        })
        return movieResult
    }

    override fun getDetailMovie(idmovie:String):LiveData<MovieEntity>{
        val movieResult = MutableLiveData<MovieEntity>()
        remoteDataSource.getDetailMovie(idmovie,object :RemoteDataSource.LoadDetailMovieCallback{
            override fun onDetailMovieReceived(tvsResponses: MovieEntity) {
                movieResult.postValue(tvsResponses)
            }
        })
        return movieResult
    }

    override fun getAllTv():LiveData<List<TvEntity>>{
        val tvResult = MutableLiveData<List<TvEntity>>()
        remoteDataSource.getAllTv(object :RemoteDataSource.LoadAllTvCallback{
            override fun onAllTvsReceived(tvsResponses: List<TvEntity>) {
                val tvlist = ArrayList<TvEntity>()
                for(response in tvsResponses){
                    tvlist.add(
                        TvEntity(
                            response.id,
                            response.titleTv,
                            response.dateTv,
                            response.descTv,
                            response.imgTv,
                        )
                    )
                }
                tvResult.postValue(tvlist)
            }
        })
        return tvResult
    }

    override fun getDetailTV(idtv:String):LiveData<TvEntity>{
        val tvResult = MutableLiveData<TvEntity>()
        remoteDataSource.getDetailTv(idtv,object :RemoteDataSource.LoadDetailTvCallback{
            override fun onDetailTvReceived(tvsResponses: TvEntity) {
                tvResult.postValue(tvsResponses)
            }
        })
        return tvResult
    }

}