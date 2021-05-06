package com.app.androidjetpack.ui.tv

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TvViewModelTest {
    private lateinit var viewModel: TvViewModel

    @Before
    fun setUp() {
        viewModel = TvViewModel()
    }

    @Test
    fun getTvs(){
        val tvEntities = viewModel.getTvs()
        assertNotNull(tvEntities)
        assertEquals(10, tvEntities.size)
    }
}