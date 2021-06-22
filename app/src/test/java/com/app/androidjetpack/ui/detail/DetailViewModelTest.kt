package com.app.androidjetpack.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovie()[0]
    private val idmovie = dummyMovie.itemId
    private val dummyTv = DataDummy.generateDummyTvs()[0]
    private val idtv = dummyTv.itemId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myRepository: MyRepository

    @Mock
    private lateinit var movieObserver: Observer<ItemEntity>

    @Mock
    private lateinit var tvObserver: Observer<ItemEntity>

    @Before
    fun setUp(){
        viewModel = DetailViewModel(myRepository)
    }

    @Test
    fun getDetailMovies() {
        val movie = MutableLiveData<ItemEntity>()
        movie.value = dummyMovie

        `when`(myRepository.getDetailMovie(idmovie)).thenReturn(movie)
        val movieEntity = viewModel.getDetailMovies(idmovie).value as ItemEntity
        verify(myRepository).getDetailMovie(idmovie)
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.itemId, movieEntity.itemId)
        assertEquals(dummyMovie.dateItem, movieEntity.dateItem)
        assertEquals(dummyMovie.description, movieEntity.description)
        assertEquals(dummyMovie.imagePath, movieEntity.imagePath)
        assertEquals(dummyMovie.title, movieEntity.title)

        viewModel.getDetailMovies(idmovie).observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getDetailTvs() {
        val tv = MutableLiveData<ItemEntity>()
        tv.value = dummyTv

        `when`(myRepository.getDetailTV(idtv)).thenReturn(tv)
        val tvEntity = viewModel.getDetailTvs(idtv).value as ItemEntity
        verify(myRepository).getDetailTV(idtv)
        assertNotNull(tvEntity)
        assertEquals(dummyTv.itemId, tvEntity.itemId)
        assertEquals(dummyTv.dateItem, tvEntity.dateItem)
        assertEquals(dummyTv.description, tvEntity.description)
        assertEquals(dummyTv.imagePath, tvEntity.imagePath)
        assertEquals(dummyTv.title, tvEntity.title)

        viewModel.getDetailTvs(idtv).observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyTv)
    }
}