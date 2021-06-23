package com.app.androidjetpack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.utils.DataDummy
import com.app.androidjetpack.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MyRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val myRepository = FakeMyRepository(remote)

    private val itemResponses = DataDummy.generateRemoteDummyMovie()

    @Test
    fun getAllMoives() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadAllMovieCallback).onAllMoviesReceived(itemResponses)
            null
        }.`when`(remote).getAllMovies(any())
        val dataEntities = LiveDataTestUtil.getValue(myRepository.getAllMovie())
        verify(remote).getAllMovies(any())
        assertNotNull(dataEntities)
        assertEquals(itemResponses.size.toLong(), dataEntities.size.toLong())
    }

    @Test
    fun getAllTvs() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadAllTvCallback).onAllTvsReceived(itemResponses)
            null
        }.`when`(remote).getAllTv(any())
        val dataEntities = LiveDataTestUtil.getValue(myRepository.getAllTv())
        verify(remote).getAllTv(any())
        assertNotNull(dataEntities)
        assertEquals(itemResponses.size.toLong(), dataEntities.size.toLong())
    }

}