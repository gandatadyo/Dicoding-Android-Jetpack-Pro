package com.app.androidjetpack.data.remote

import android.util.Log
import android.widget.Toast
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.utils.ModulRestapi
import org.json.JSONException
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
            val movie = ArrayList<ItemResponseEntity>()
            val list = JSONObject(it).getJSONArray("results")
            for(i in 0 until list.length()){
                try {
                    movie.add(
                        ItemResponseEntity(
                            list.getJSONObject(i).getString("id"),
                            list.getJSONObject(i).getString("original_title"),
                            list.getJSONObject(i).getString("release_date"),
                            list.getJSONObject(i).getString("overview"),
                            list.getJSONObject(i).getString("backdrop_path"),
                        )

                    )
                }catch(e:JSONException){
                    Log.d("error",e.toString())
                }
            }
            callback.onAllMoviesReceived(movie)
        },{
            Log.d("test","error") // nothing
        })
    }

    fun getAllTv(callback: LoadAllTvCallback){
        modulRestapi.requestHttp("/3/discover/tv",{
            val tv = ArrayList<ItemResponseEntity>()
            val list = JSONObject(it).getJSONArray("results")
            for(i in 0 until list.length()){
                try {
                    tv.add(
                        ItemResponseEntity(
                            list.getJSONObject(i).getString("id"),
                            list.getJSONObject(i).getString("original_name"),
                            list.getJSONObject(i).getString("first_air_date"),
                            list.getJSONObject(i).getString("overview"),
                            list.getJSONObject(i).getString("backdrop_path"),
                        )
                    )
                }catch(e:JSONException){
                    Log.d("error",e.toString())
                }
            }
            callback.onAllTvsReceived(tv)
        },{
            Log.d("test","error") // nothing
        })
    }

    fun getDetailMovie(idtv:String,callback: LoadDetailMovieCallback){
        modulRestapi.requestHttp("/3/movie/${idtv}",{
            val item = ItemResponseEntity(
                idtv,
                JSONObject(it).getString("original_title"),
                JSONObject(it).getString("release_date"),
                JSONObject(it).getString("overview"),
                JSONObject(it).getString("poster_path")
            )
            callback.onDetailMovieReceived(item)
        },{
            Log.d("test","error") // nothing
        })
    }

    fun getDetailTv(idtv:String,callback: LoadDetailTvCallback){
        modulRestapi.requestHttp("/3/tv/${idtv}",{
            callback.onDetailTvReceived(ItemResponseEntity(
                idtv,
                JSONObject(it).getString("original_name"),
                JSONObject(it).getString("first_air_date"),
                JSONObject(it).getString("overview"),
                JSONObject(it).getString("poster_path")
            ))
        },{
            Log.d("test","error") // nothing
        })
    }

    interface LoadAllMovieCallback {
        fun onAllMoviesReceived(moviesResponses: List<ItemResponseEntity>)
    }

    interface LoadAllTvCallback {
        fun onAllTvsReceived(tvsResponses: List<ItemResponseEntity>)
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieReceived(tvsResponses: ItemResponseEntity)
    }

    interface LoadDetailTvCallback {
        fun onDetailTvReceived(tvsResponses: ItemResponseEntity)
    }

}