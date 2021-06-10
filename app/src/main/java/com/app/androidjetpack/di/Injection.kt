package com.app.androidjetpack.di

import android.content.Context

interface Injection {
    fun provideRepository(context: Context): AcademyRepository {

        val remoteRepository = RemoteDataSource.getInstance(JsonHelper(context))

        return AcademyRepository.getInstance(remoteRepository)
    }
}