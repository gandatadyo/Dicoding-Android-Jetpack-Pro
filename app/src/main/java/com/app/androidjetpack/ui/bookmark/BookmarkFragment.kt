package com.app.androidjetpack.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.androidjetpack.databinding.FragmentBookmarkmainBinding

class BookmarkFragment: Fragment() {

    private lateinit var fragmentMovieBinding: FragmentBookmarkmainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMovieBinding = FragmentBookmarkmainBinding.inflate(layoutInflater, container, false)

        val sectionsPagerAdapter = BookmarkPagerAdapter(requireContext(), childFragmentManager)
        fragmentMovieBinding.viewPager.adapter = sectionsPagerAdapter
        fragmentMovieBinding.tabs.setupWithViewPager(fragmentMovieBinding.viewPager)

        return fragmentMovieBinding.root
    }
}