package com.app.androidjetpack.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

class RecyclerViewItemCountAssertion(
    private val matcher: Matcher<Int>
) : ViewAssertion {

    companion object {
        @JvmStatic
        fun withItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
            return withItemCount(CoreMatchers.`is`(expectedCount))
        }

        @JvmStatic
        fun withItemCount(matcher: Matcher<Int>): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(matcher)
        }
    }

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        ViewMatchers.assertThat(adapter?.itemCount, matcher)
    }
}