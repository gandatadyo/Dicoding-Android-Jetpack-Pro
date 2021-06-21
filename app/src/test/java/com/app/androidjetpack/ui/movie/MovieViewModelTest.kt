package com.app.androidjetpack.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.utils.DataDummy
import junit.framework.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest  {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myRepository: MyRepository

    @Mock
    private lateinit var observer: Observer<List<ItemEntity>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(myRepository)
    }

    @Test
    fun getMovies(){
        val dummyMovie = DataDummy.generateDummyMovie()
        val movies = MutableLiveData<List<ItemEntity>>()
        movies.value = dummyMovie

        `when`(myRepository.getAllMovie()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value
        verify(myRepository).getAllMovie()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}