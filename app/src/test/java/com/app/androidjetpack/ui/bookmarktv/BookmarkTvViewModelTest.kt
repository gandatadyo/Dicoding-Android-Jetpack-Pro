package com.app.androidjetpack.ui.bookmarktv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.utils.DataDummy
import com.app.androidjetpack.utils.SortUtils
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookmarkTvViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myRepository: MyRepository

    private lateinit var viewModel: BookmarkTvViewModel

    private val dummyMovies = DataDummy.generateDummyMovie()

    @Before
    fun setUp() {
        viewModel = BookmarkTvViewModel(myRepository)
    }

    @Test
    fun getBookmarks() {

        val pagedList = mock<PagedList<ItemTvEntity>>()
        Mockito.`when`(pagedList.loadedCount).thenReturn(5)
        Mockito.`when`(pagedList.size).thenReturn(dummyMovies.size)

        val data = MutableLiveData<PagedList<ItemTvEntity>>().apply {
            value = pagedList
        }

        Mockito.`when`(myRepository.getBookmarkedTvs(SortUtils.ASCENDING)).thenReturn(data)
        val items = viewModel.getBookmarks(SortUtils.ASCENDING)
        verify(myRepository).getBookmarkedTvs(SortUtils.ASCENDING)

        assertNotNull(items.value)
        assertEquals(5, data.value!!.loadedCount)
        assertEquals(dummyMovies.size, data.value!!.size)

    }
}