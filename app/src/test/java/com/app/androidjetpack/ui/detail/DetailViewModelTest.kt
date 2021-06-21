package com.app.androidjetpack.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.utils.DataDummy
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
    private val dummyCourse = DataDummy.generateDummyMovie()[0]
    private val courseId = dummyCourse.itemId
//    private val dummyModules = DataDummy.generateDummyModules(courseId)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myRepository: MyRepository

    @Mock
    private lateinit var courseObserver: Observer<ItemEntity>

    @Mock
    private lateinit var modulesObserver: Observer<List<ItemEntity>>

    @Before
    fun setUp(){
        viewModel = DetailViewModel(myRepository)
//        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getDetailMovies() {
        val course = MutableLiveData<ItemEntity>()
        course.value = dummyCourse

    }

    @Test
    fun getDetailTvs() {

    }
}