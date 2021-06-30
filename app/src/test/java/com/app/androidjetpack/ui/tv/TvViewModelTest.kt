package com.app.androidjetpack.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.utils.DataDummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
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
        //TODO Test, seperti MovieViewModelTest
    }

    @Test
    fun `getTVs returns error data is null`() {
        //TODO
    }
}