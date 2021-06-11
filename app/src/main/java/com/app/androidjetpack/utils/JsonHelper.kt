package com.app.androidjetpack.utils

import android.content.Context
import com.app.androidjetpack.R
import com.app.androidjetpack.data.source.remote.response.ItemEntityResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): List<ItemEntityResponse> {
        val list = ArrayList<ItemEntityResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("CourseResponses.json").toString())
            val listArray = responseObject.getJSONArray("courses")
            for (i in 0 until listArray.length()) {
                val course = listArray.getJSONObject(i)

                val id = course.getString("itemId")
                val title = course.getString("title")
                val description = course.getString("description")
                val date = course.getString("dateItem")

                val courseResponse = ItemEntityResponse(id, title, description, date, R.drawable.poster_arrow)
                list.add(courseResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }
}