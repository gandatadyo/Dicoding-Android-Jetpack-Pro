package com.app.androidjetpack.utils

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.app.androidjetpack.data.entity.MovieEntity
import org.json.JSONObject


class ModulRestapi {
    private val mainUrl = "https://api.themoviedb.org"
    private val apikey = "1c82c78abfacc7ee0508966c9489f84c"

    fun requestHttp(pathUrl:String,resultSuccess:(String)-> Unit,resultError:(String)->Unit){
        AndroidNetworking.get("$mainUrl$pathUrl?api_key=${apikey}")
            .setPriority(Priority.LOW)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    resultSuccess(response.toString())
                }

                override fun onError(anError: ANError?) {
                    resultError(anError.toString())
                }

            })
    }
}