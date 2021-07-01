package com.app.androidjetpack.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.utils.DataDummy
import com.app.androidjetpack.vo.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myRepository: MyRepository

    private lateinit var viewModel: TvViewModel

    private val dummyTVs = DataDummy.generateDummyTvs()

    @Before
    fun setUp() {
        viewModel = TvViewModel(myRepository)
    }

    @Test
    fun `getTVs returns success`(){
        val pagedList = mock<PagedList<ItemTvEntity>>()
        val resource = Resource.success(pagedList)
        Mockito.`when`(pagedList.loadedCount).thenReturn(5)
        Mockito.`when`(pagedList.size).thenReturn(dummyTVs.size)

        val list = MutableLiveData<Resource<PagedList<ItemTvEntity>>>().apply {
            value = resource
        }

        Mockito.`when`(myRepository.getAllTv()).thenReturn(list)
        val movies = viewModel.getTV()
        verify(myRepository).getAllTv()

        Assert.assertNotNull(movies.value)
        Assert.assertEquals(5, list.value!!.data!!.loadedCount)
        Assert.assertEquals(dummyTVs.size, list.value?.data?.size)
    }

    @Test
    fun `getTVs returns error data is null`() {
        val resource = Resource.error("Terjadi kesalahan", null)

        val list = MutableLiveData<Resource<PagedList<ItemTvEntity>>>().apply {
            value = resource
        }

        Mockito.`when`(myRepository.getAllTv()).thenReturn(list)
        val items = viewModel.getTV()
        verify(myRepository).getAllTv()

        Assert.assertNull(items.value?.data)
        Assert.assertEquals(items.value!!.message, "Terjadi kesalahan")
    }
}