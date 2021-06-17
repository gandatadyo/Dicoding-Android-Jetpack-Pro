package com.app.androidjetpack.di

import android.content.Context
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.utils.ModulRestapi

object Injection {

    fun provideRepository(): MyRepository {
        val remoteRepository = RemoteDataSource.getInstance(ModulRestapi())
        return MyRepository.getInstance(remoteRepository)
    }

}