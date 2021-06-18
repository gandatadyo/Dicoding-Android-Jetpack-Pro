package com.app.androidjetpack.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.app.androidjetpack.R
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.data.entity.TvEntity
import com.app.androidjetpack.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest {
    private var dummyMovies = ArrayList<MovieEntity>()
    private var dummyTvs = ArrayList<TvEntity>()

    private lateinit var instrumentalContext: Context

    @Before
    fun setUp() {
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }


//    @get:Rule
//    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadMovies() {
//        onView(withId(R.id.lblDataIdlingResource)).check(matches(withText(instrumentalContext.getString(R.string.prepare))))
//        onView(withText(instrumentalContext.getString(R.string.start))).perform(click())
//        onView(withId(R.id.lblDataIdlingResource)).check(matches(withText(instrumentalContext.getString(R.string.delay1))))

        onView(withId(R.id.rvMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

//    @Test
//    fun loadDetailMovies() {
//        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
//        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
//        onView(withId(R.id.text_title)).check(matches(withText(dummyMovies[0].title)))
//        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
//        onView(withId(R.id.text_date)).check(matches(withText("Tanggal Rilis ${dummyMovies[0].dateItem}")))
//    }

//    @Test
//    fun loadTvs() {
//        onView(withText("TV Show")).perform(click())
//        onView(withId(R.id.rvTv)).check(matches(isDisplayed()))
//        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvs.size))
//    }

//    @Test
//    fun loadDetailTvs() {
//        onView(withText("TV Show")).perform(click())
//        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
//        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
//        onView(withId(R.id.text_title)).check(matches(withText(dummyTvs[0].title)))
//        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
//        onView(withId(R.id.text_date)).check(matches(withText("Tanggal Rilis ${dummyTvs[0].dateItem}")))
//        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
//        onView(withId(R.id.text_description)).check(matches(withText(dummyTvs[0].description)))
//    }
}