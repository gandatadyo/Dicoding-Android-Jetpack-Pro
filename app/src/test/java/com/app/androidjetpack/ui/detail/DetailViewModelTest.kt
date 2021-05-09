package com.app.androidjetpack.ui.detail

import com.app.androidjetpack.utils.DataDummy
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyItem = DataDummy.generateDummyMovie()[0]
    private val itemId = dummyItem.itemId

    @Before
    fun setUp() {
        viewModel = DetailViewModel()
        viewModel.setSelectedData(itemId)
    }

    @Test
    fun getDetail() {
        viewModel.setSelectedData(dummyItem.itemId)
        val dataEntity = viewModel.getData("movie")
        assertNotNull(dataEntity)
        if(dataEntity!=null) {
            assertEquals(dummyItem.itemId, dataEntity.itemId)
            assertEquals(dummyItem.title, dataEntity.title)
            assertEquals(dummyItem.dateItem, dataEntity.dateItem)
            assertEquals(dummyItem.description, dataEntity.description)
            assertEquals(dummyItem.imagePath, dataEntity.imagePath)
        }
    }
}