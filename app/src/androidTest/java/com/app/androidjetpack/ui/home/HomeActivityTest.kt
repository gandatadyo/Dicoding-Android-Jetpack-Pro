package com.app.androidjetpack.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.app.androidjetpack.R
import com.app.androidjetpack.utils.DataDummy
import com.app.androidjetpack.utils.EspressoIdlingResource
import com.app.androidjetpack.utils.RecyclerViewItemCountAssertion.Companion.withItemCount
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest {
    private var dummyMovies = DataDummy.generateDummyMovie()
    private var dummyTvs = DataDummy.generateDummyTvs()

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

    @Test
    fun loadMovies() {
        onView(withId(R.id.rvMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvs() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rvTv)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvs.size))
    }

    @Test
    fun loadDetailTvs() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
    }

    @Test
    fun loadMoviesAddDeleteFavorite() {
        onView(withText("Movie")).perform(click())
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.action_bookmark)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withText("Bookmark")).perform(click())
        onView(withText("Movie Bookmark")).perform(click())
        onView(withId(R.id.rvBookmarkMovie)).check(matches(isDisplayed()))

        onView(withId(R.id.rvBookmarkMovie)).check(withItemCount(1))
        onView(withId(R.id.rvBookmarkMovie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rvBookmarkMovie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.action_bookmark)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.rvBookmarkMovie)).check(withItemCount(Matchers.lessThan(1)))
    }

    @Test
    fun loadTvsAddDeleteFavorite() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rvTv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.action_bookmark)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withText("Bookmark")).perform(click())
        onView(withText("TV Show Bookmark")).perform(click())
        onView(withId(R.id.rvBookmarkTV)).check(matches(isDisplayed()))

        onView(withId(R.id.rvBookmarkTV)).check(withItemCount(1))
        onView(withId(R.id.rvBookmarkTV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rvBookmarkTV)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.action_bookmark)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.rvBookmarkTV)).check(withItemCount(Matchers.lessThan(1)))
    }
}