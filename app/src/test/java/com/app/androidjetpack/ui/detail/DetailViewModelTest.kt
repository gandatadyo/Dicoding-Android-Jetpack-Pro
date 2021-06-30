package com.app.androidjetpack.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.utils.DataDummy
import com.app.androidjetpack.vo.Resource
import com.app.androidjetpack.vo.Status
import org.junit.Assert
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
    private lateinit var movieObserver: Observer<Resource<ItemMovieEntity>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<ItemTvEntity>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(myRepository)
    }

    @Test
    fun `getDetailMovie returns success`() {
        //TODO Test, pastikan setiap fungsi test juga dilakukan assertion (assertNotNull, assertEquals, dll)
        //Test mirip detail tv returns success
        // viewModel.itemModule untuk memanggil repository getDetailMovie belum ada,
        // ataukah menggunakan getDetailMovie(idmovie:String) ?
    }

    @Test
    fun `getDetailMovie returns error`() {
        //Test seperti getDetailTV returns error
    }

    @Test
    fun `getDetailTV returns success`() {
        val resource = Resource.success(dummyTv)
        val data = MutableLiveData<Resource<ItemTvEntity>>().apply {
            value = resource
        }

        `when`(myRepository.getDetailTV(idtv)).thenReturn(data)

        viewModel.setSelectedItem(idtv)
        viewModel.itemTvModule.observeForever(tvObserver)

        verify(myRepository).getDetailTV(idtv)
        verify(tvObserver).onChanged(resource)

        assertNotNull(data.value?.data)
        assertEquals(data.value!!.data, dummyTv)
        assertEquals(data.value!!.status, Status.SUCCESS)
    }

    @Test
    fun `getDetailTV returns error`() {
        val resource = Resource.error("Terjadi kesalahan", null)
        val data = MutableLiveData<Resource<ItemTvEntity>>().apply {
            value = resource
        }

        `when`(myRepository.getDetailTV(idtv)).thenReturn(data)

        viewModel.setSelectedItem(idtv)
        viewModel.itemTvModule.observeForever(tvObserver)

        verify(myRepository).getDetailTV(idtv)
        verify(tvObserver).onChanged(resource)

        Assert.assertNull(data.value?.data)
        assertEquals(data.value!!.status, Status.ERROR)
        assertEquals(data.value!!.message, "Terjadi kesalahan")
    }
}