package com.app.androidjetpack.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.androidjetpack.databinding.FragmentBookmarkBinding
import com.app.androidjetpack.ui.movie.MovieAdapter
import com.app.androidjetpack.utils.EspressoIdlingResource
import com.app.androidjetpack.viewmodel.ViewModelFactory

class BookmarkFragment : Fragment() {
    private lateinit var fragmentBookmarkBinding: FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBookmarkBinding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        fragmentBookmarkBinding.loadingView.visibility = View.GONE
        return fragmentBookmarkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this,factory)[BookmarkViewModel::class.java]

            val academyAdapter = MovieAdapter()
            EspressoIdlingResource.increment()
            fragmentBookmarkBinding.loadingView.visibility = View.VISIBLE
            viewModel.getBookmarks().observe(requireActivity(), { movies ->
                fragmentBookmarkBinding.loadingView.visibility = View.GONE
                academyAdapter.setMovies(movies)
                academyAdapter.notifyDataSetChanged()
            })

            with(fragmentBookmarkBinding.rvBookmark) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = academyAdapter
            }
        }
    }
}