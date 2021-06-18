package com.app.androidjetpack.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.di.Injection
import com.app.androidjetpack.ui.movie.MovieDataViewModel
import com.app.androidjetpack.ui.tv.TvDataViewModel

class ViewModelFactory private constructor(private val myRepository: MyRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository()).apply { instance = this }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieDataViewModel::class.java) -> {
                MovieDataViewModel(myRepository) as T
            }
            modelClass.isAssignableFrom(TvDataViewModel::class.java) -> {
                TvDataViewModel(myRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}