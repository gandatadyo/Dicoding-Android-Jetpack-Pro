package com.app.androidjetpack.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.androidjetpack.R
import com.app.androidjetpack.ui.movie.MovieFragment
import com.app.androidjetpack.ui.tv.TvFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager):FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.tvshow)
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(TAB_TITLES[position])

    override fun getItem(position: Int): Fragment {
        lateinit var fragment:Fragment
        when (position) {
            0 -> fragment = MovieFragment()
            1 -> fragment = TvFragment()
            else -> Fragment()
        }
        return fragment
    }
}