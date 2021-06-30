package com.app.androidjetpack.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    const val ASCENDING = "ASC"
    const val DESCENDING = "DESC"

    fun getBookmarkedMovieQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movieentities WHERE bookmarked = 1 ")
        simpleQuery.append("ORDER BY title $filter")

        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getBookmarkedTvQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM tventities WHERE bookmarked = 1 ")
        simpleQuery.append("ORDER BY title $filter")

        return SimpleSQLiteQuery(simpleQuery.toString())
    }

}