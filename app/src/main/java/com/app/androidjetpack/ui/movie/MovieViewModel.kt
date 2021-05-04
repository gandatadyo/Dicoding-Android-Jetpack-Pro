package com.app.androidjetpack.ui.movie

import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MovieEntity
import com.app.androidjetpack.utils.DataDummy

class MovieViewModel: ViewModel() {
    fun getCourses(): List<MovieEntity> = DataDummy.generateDummyCourses()
}