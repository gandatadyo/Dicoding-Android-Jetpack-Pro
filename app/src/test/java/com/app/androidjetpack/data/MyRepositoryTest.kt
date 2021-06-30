package com.app.androidjetpack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.app.androidjetpack.data.remote.ApiResponse
import com.app.androidjetpack.data.remote.RemoteDataSource
import com.app.androidjetpack.data.remote.StatusResponse
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.LocalDataSource
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.utils.*
import com.app.androidjetpack.vo.Resource
import com.app.androidjetpack.vo.Status
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MyRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val observerMovie = mock<Observer<Resource<ItemMovieEntity>>>()
    private val observerTv = mock<Observer<Resource<ItemTvEntity>>>()

    private val remote = mock<RemoteDataSource>()

    @Mock
    private lateinit var local: LocalDataSource

    private lateinit var myRepository: FakeMyRepository

    private val movieId = "460465"
    private val movieResponses = DataDummy.generateRemoteDummyMovie()
    private val movieEntities = DataDummy.generateDummyMovie()

    private val tvId = "84958"
    private val tvResponses = DataDummy.generateRemoteDummyTvs()
    private val tvEntities = DataDummy.generateDummyTvs()

    @Before
    fun setUp() {
        myRepository = FakeMyRepository(remote, local, InstantAppExecutors())
    }

    @Test
    fun `getAllMovies, load from db, returns success and data is not null`() {

        val pagedList = Resource.success(PagedListUtil.mockPagedList(movieEntities))
        Mockito.`when`(pagedList.data?.size).thenReturn(20)

        val data = MutableLiveData<Resource<PagedList<ItemMovieEntity>>>().apply {
            value = pagedList
        }

        val factory = mock<DataSource.Factory<Int, ItemMovieEntity>>()
        Mockito.`when`(local.getAllMovies()).thenReturn(factory)

        myRepository.getAllMovie()
        verify(local).getAllMovies()
        verify(remote, never()).getAllMovies()
        verify(local, never()).insertMovies(any())

        assertNotNull(data.value)
        assertEquals(data.value!!.data!!.size, 20)
        assertEquals(data.value!!.status, Status.SUCCESS)

    }

    @Test
    fun `get detail movie, load from db`() {

        val dbData = MutableLiveData<ItemMovieEntity>().apply {
            value = movieEntities[0]
        }
        Mockito.`when`(local.getMovieDetail(movieId)).thenReturn(dbData)

        myRepository.getDetailMovie(movieId)

        verify(local).getMovieDetail(movieId)
        verify(remote, never()).getDetailMovie(movieId)
        verify(local, never()).insertMovies(any())

        assertNotNull(dbData.value)
        assertEquals(dbData.value!!.itemId, movieEntities[0].itemId)
    }

    @Test
    fun `get detail movie from network, returns success, and data is not null`() {

        val dbData = MutableLiveData<ItemMovieEntity>()
        Mockito.`when`(local.getMovieDetail(movieId)).thenReturn(dbData)

        val itemResponse = movieResponses[0]
        val apiSuccess = ApiResponse.success(itemResponse)
        val dataResponse = MutableLiveData<ApiResponse<ItemResponseEntity>>().apply {
            value = apiSuccess
        }

        Mockito.`when`(remote.getDetailMovie(movieId)).thenReturn(dataResponse)

        myRepository.getDetailMovie(movieId).observeForever(observerMovie)

        dbData.postValue(null)
        verify(remote).getDetailMovie(movieId)
        verify(local).insertMovies(any())

        assertNotNull(dataResponse.value)
        assertEquals(dataResponse.value!!.body, movieResponses[0])
        assertEquals(dataResponse.value!!.status, StatusResponse.SUCCESS)
    }

    @Test
    fun `getAllTvs, load from db, data is not null`() {
        //TODO: Test, mirip getAllMovies load from db
    }

    @Test
    fun `getDetailTV, load from db`() {
        //Test, mirip get detail tv from db
    }

    @Test
    fun `getDetailTV, load from network`() {
        //Test, mirip get detail tv from network
    }

    @Test
    fun `get bookmarked movie from local, data is not null`() {
        val movies = PagedListUtil.mockPagedList(movieEntities.toList())

        Mockito.`when`(movies.size).thenReturn(movieEntities.size)

        val dbData = MutableLiveData<PagedList<ItemMovieEntity>>().apply {
            value = movies
        }

        val factory = mock<DataSource.Factory<Int, ItemMovieEntity>>()
        Mockito.`when`(local.getBookmarkedMovies(SortUtils.ASCENDING)).thenReturn(factory)

        myRepository.getBookmarkedMovies(SortUtils.ASCENDING)
        verify(local).getBookmarkedMovies(SortUtils.ASCENDING)

        assertNotNull(dbData.value)
        assertEquals(dbData.value!!.size, movieEntities.size)
    }

}