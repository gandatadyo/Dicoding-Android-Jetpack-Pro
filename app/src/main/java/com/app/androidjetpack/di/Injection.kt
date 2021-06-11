package com.app.androidjetpack.di

import android.content.Context
import com.app.androidjetpack.data.AcademyRepository
import com.app.androidjetpack.data.source.remote.RemoteDataSource
import com.app.androidjetpack.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository {
        val remoteRepository = RemoteDataSource.getInstance(JsonHelper(context))
        return AcademyRepository.getInstance(remoteRepository)
    }
}