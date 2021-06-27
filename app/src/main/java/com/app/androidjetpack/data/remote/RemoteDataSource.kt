package com.app.androidjetpack.data.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.utils.EspressoIdlingResource
import com.app.androidjetpack.utils.ModulRestapi
import org.json.JSONException
import org.json.JSONObject

class RemoteDataSource private constructor(private val modulRestapi: ModulRestapi){

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: ModulRestapi): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies():LiveData<ApiResponse<List<ItemResponseEntity>>> {
        EspressoIdlingResource.increment()
        val resultItems = MutableLiveData<ApiResponse<List<ItemResponseEntity>>>()
        handler.postDelayed({
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
                resultItems.value = ApiResponse.success(movie)
                EspressoIdlingResource.decrement()
            },{
                Log.d("test","error") // nothing
                EspressoIdlingResource.decrement()
            })

        }, SERVICE_LATENCY_IN_MILLIS)
        return resultItems
    }

    fun getAllTv():LiveData<ApiResponse<List<ItemResponseEntity>>>{
        EspressoIdlingResource.increment()
        val resultItems = MutableLiveData<ApiResponse<List<ItemResponseEntity>>>()
        handler.postDelayed({
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
                resultItems.value = ApiResponse.success(tv)
                EspressoIdlingResource.decrement()
            },{
                Log.d("test","error") // nothing
                EspressoIdlingResource.decrement()
            })

        }, SERVICE_LATENCY_IN_MILLIS)
        return resultItems
    }

    fun getDetailMovie(idmovie:String):LiveData<ApiResponse<ItemResponseEntity>> {
        EspressoIdlingResource.increment()
        val resultModules = MutableLiveData<ApiResponse<ItemResponseEntity>>()
        handler.postDelayed({
            modulRestapi.requestHttp("/3/movie/${idmovie}",{
                val item = ItemResponseEntity(
                    idmovie,
                    JSONObject(it).getString("original_title"),
                    JSONObject(it).getString("release_date"),
                    JSONObject(it).getString("overview"),
                    JSONObject(it).getString("poster_path")
                )
                resultModules.value = ApiResponse.success(item)
            },{
                Log.d("test","error") // nothing
            })
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultModules
    }

    fun getDetailTv(idtv:String):LiveData<ApiResponse<ItemResponseEntity>> {
        EspressoIdlingResource.increment()
        val resultModules = MutableLiveData<ApiResponse<ItemResponseEntity>>()
        handler.postDelayed({
            modulRestapi.requestHttp("/3/tv/${idtv}",{
                val item = ItemResponseEntity(
                    idtv,
                    JSONObject(it).getString("original_name"),
                    JSONObject(it).getString("first_air_date"),
                    JSONObject(it).getString("overview"),
                    JSONObject(it).getString("poster_path")
                )
                resultModules.value = ApiResponse.success(item)
            },{
                Log.d("test","error") // nothing
            })
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultModules
    }
}