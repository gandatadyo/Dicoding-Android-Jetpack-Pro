package com.app.androidjetpack.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myRepository: MyRepository

    @Mock
    private lateinit var observer: Observer<List<ItemEntity>>

    @Before
    fun setUp() {
        viewModel = TvViewModel(myRepository)
    }

    @Test
    fun getTvs(){
        val dummyTv = DataDummy.generateDummyTvs()
        val tvs = MutableLiveData<List<ItemEntity>>()
        tvs.value = dummyTv

        `when`(myRepository.getAllTv()).thenReturn(tvs)
        val tvEntities = viewModel.getTV().value
        verify(myRepository).getAllTv()
        assertNotNull(tvEntities)
        assertEquals(10, tvEntities?.size)

        viewModel.getTV().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}