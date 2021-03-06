package com.app.androidjetpack.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.di.Injection
import com.app.androidjetpack.ui.bookmarkmovie.BookmarkMovieViewModel
import com.app.androidjetpack.ui.bookmarktv.BookmarkTvViewModel
import com.app.androidjetpack.ui.detail.DetailViewModel
import com.app.androidjetpack.ui.movie.MovieViewModel
import com.app.androidjetpack.ui.tv.TvViewModel

class ViewModelFactory private constructor(private val myRepository: MyRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context:Context): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository(context)).apply { instance = this }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(myRepository) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(myRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(myRepository) as T
            }
            modelClass.isAssignableFrom(BookmarkMovieViewModel::class.java) -> {
                BookmarkMovieViewModel(myRepository) as T
            }
            modelClass.isAssignableFrom(BookmarkTvViewModel::class.java) -> {
                BookmarkTvViewModel(myRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}