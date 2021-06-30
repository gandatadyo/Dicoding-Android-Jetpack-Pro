package com.app.androidjetpack.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.utils.DataDummy
import com.app.androidjetpack.vo.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest  {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myRepository: MyRepository

    private lateinit var viewModel: MovieViewModel

    private val dummyMovies = DataDummy.generateDummyMovie()

    @Before
    fun setUp() {
        viewModel = MovieViewModel(myRepository)
    }

    @Test
    fun `getMovies returns success`(){
        val pagedList = mock<PagedList<ItemMovieEntity>>()
        val resource = Resource.success(pagedList)
        `when`(pagedList.loadedCount).thenReturn(5)
        `when`(pagedList.size).thenReturn(dummyMovies.size)

        val list = MutableLiveData<Resource<PagedList<ItemMovieEntity>>>().apply {
            value = resource
        }

        `when`(myRepository.getAllMovie()).thenReturn(list)
        val movies = viewModel.getMovies()
        verify(myRepository).getAllMovie()

        assertNotNull(movies.value)
        assertEquals(5, list.value!!.data!!.loadedCount)
        assertEquals(dummyMovies.size, list.value!!.data!!.size)
    }

    @Test
    fun `getMovies returns error, data is null`(){
        val resource = Resource.error("Terjadi kesalahan", null)

        val list = MutableLiveData<Resource<PagedList<ItemMovieEntity>>>().apply {
            value = resource
        }

        `when`(myRepository.getAllMovie()).thenReturn(list)
        val items = viewModel.getMovies()
        verify(myRepository).getAllMovie()

        assertNull(items.value?.data)
        assertEquals(items.value!!.message, "Terjadi kesalahan")
    }
}