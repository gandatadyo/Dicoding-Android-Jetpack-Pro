package com.app.androidjetpack.di

import android.content.Context
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.data.source.local.LocalDataSource
import com.app.androidjetpack.data.source.local.room.MovieTvDatabase
import com.app.androidjetpack.utils.AppExecutors
import com.app.androidjetpack.utils.ModulRestapi

object Injection {

    fun provideRepository(context: Context): MyRepository {

        val database = MovieTvDatabase.getInstance(context)

        val remoteRepository = RemoteDataSource.getInstance(ModulRestapi())
        val localDataSource = LocalDataSource.getInstance(database.movieTvDao())
        val appExecutors = AppExecutors()

        return MyRepository.getInstance(remoteRepository,localDataSource, appExecutors)
    }

}