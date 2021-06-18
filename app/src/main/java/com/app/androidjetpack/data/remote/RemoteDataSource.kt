package com.app.androidjetpack.data.remote

import android.util.Log
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.data.entity.TvEntity
import com.app.androidjetpack.utils.ModulRestapi
import org.json.JSONObject

class RemoteDataSource private constructor(private val modulRestapi: ModulRestapi){

    companion object {

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: ModulRestapi): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies(callback: LoadAllMovieCallback){
        modulRestapi.requestHttp("/3/discover/movie",{
            val movie = ArrayList<MovieEntity>()
            val list = JSONObject(it).getJSONArray("results")
            for(i in 0 until list.length()){
                movie.add(
                    MovieEntity(
                        list.getJSONObject(i).getString("id"),
                        list.getJSONObject(i).getString("original_title"),
                        list.getJSONObject(i).getString("release_date"),
                        list.getJSONObject(i).getString("overview"),
                        list.getJSONObject(i).getString("backdrop_path"),
                    )

                )
            }
            callback.onAllMoviesReceived(movie)
        },{
            Log.d("test","error") // nothing
        })
    }

    fun getAllTv(callback: LoadAllTvCallback){
        modulRestapi.requestHttp("/3/discover/tv",{
            val tv = ArrayList<TvEntity>()
            val list = JSONObject(it).getJSONArray("results")
            for(i in 0 until list.length()){
                tv.add(
                    TvEntity(
                        list.getJSONObject(i).getString("id"),
                        list.getJSONObject(i).getString("original_name"),
                        list.getJSONObject(i).getString("first_air_date"),
                        list.getJSONObject(i).getString("overview"),
                        list.getJSONObject(i).getString("backdrop_path"),
                    )
                )
            }
            callback.onAllTvsReceived(tv)
        },{
            Log.d("test","error") // nothing
        })
    }

    interface LoadAllMovieCallback {
        fun onAllMoviesReceived(moviesResponses: List<MovieEntity>)
    }

    interface LoadAllTvCallback {
        fun onAllTvsReceived(tvsResponses: List<TvEntity>)
    }

}