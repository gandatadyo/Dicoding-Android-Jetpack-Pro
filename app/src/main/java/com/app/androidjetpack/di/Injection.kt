package com.app.androidjetpack.di

import android.content.Context
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.data.source.local.LocalDataSource
import com.app.androidjetpack.data.source.local.room.AcademyDatabase
import com.app.androidjetpack.utils.AppExecutors
import com.app.androidjetpack.utils.ModulRestapi

object Injection {

    fun provideRepository(context: Context): MyRepository {

        val database = AcademyDatabase.getInstance(context)

        val remoteRepository = RemoteDataSource.getInstance(ModulRestapi())
        val localDataSource = LocalDataSource.getInstance(database.academyDao())
        val appExecutors = AppExecutors()

        return MyRepository.getInstance(remoteRepository,localDataSource, appExecutors)
    }

}