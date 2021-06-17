package com.app.androidjetpack.data.remote

import android.util.Log
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.utils.ModulRestapi
import org.json.JSONObject

class RemoteDataSource private constructor(private val modulRestapi: ModulRestapi){

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

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
            callback.onAllCoursesReceived(movie)
        },{
            Log.d("test","error") // nothing
        })
    }

    interface LoadAllMovieCallback {
        fun onAllCoursesReceived(courseResponses: List<MovieEntity>)
    }

}