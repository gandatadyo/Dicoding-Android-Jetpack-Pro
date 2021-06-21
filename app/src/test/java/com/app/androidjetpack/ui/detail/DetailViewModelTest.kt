package com.app.androidjetpack.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private var idmovie = "337404"

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myRepository: MyRepository

    @Mock
    private lateinit var observer: Observer<ItemResponseEntity>

    @Before
    fun setUp(){
        viewModel = DetailViewModel(myRepository)
    }

    @Test
    fun getDetailMovies() {
        val dummyMovie = viewModel.getDetailMovies(idmovie)
        val course = MutableLiveData<ItemResponseEntity>()
//        course.value = dummyMovie
        val movie = MutableLiveData<List<ItemResponseEntity>>()
        `when`(myRepository.getDetailTV(idmovie)).thenReturn(dummyMovie)

//        viewModel.getDetailMovies(idmovie).observeForever(observer)
//        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun getDetailTvs() {

    }
}