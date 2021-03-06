package com.app.androidjetpack.ui.bookmarkmovie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.androidjetpack.databinding.FragmentBookmarkMovieBinding
import com.app.androidjetpack.ui.detail.DetailItemActivity
import com.app.androidjetpack.ui.movie.MovieAdapter
import com.app.androidjetpack.utils.SortUtils
import com.app.androidjetpack.viewmodel.ViewModelFactory

class BookmarkMovieFragment : Fragment() {
    private lateinit var fragmentBookmarkBinding: FragmentBookmarkMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBookmarkBinding = FragmentBookmarkMovieBinding.inflate(layoutInflater, container, false)
        fragmentBookmarkBinding.loadingView.visibility = View.GONE
        return fragmentBookmarkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this,factory)[BookmarkMovieViewModel::class.java]

            val itemAdapter = MovieAdapter { itemId: String, mode: String ->showDetail(itemId,mode) }
            fragmentBookmarkBinding.loadingView.visibility = View.VISIBLE
            viewModel.getBookmarks(SortUtils.ASCENDING).observe(requireActivity(), { movies ->
                fragmentBookmarkBinding.loadingView.visibility = View.GONE
                itemAdapter.submitList(movies)
            })

            with(fragmentBookmarkBinding.rvBookmarkMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = itemAdapter
            }
        }
    }

    private fun showDetail(itemId: String, mode: String){
        val intent = Intent(requireContext(), DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.EXTRA_ITEM, itemId)
        intent.putExtra(DetailItemActivity.EXTRA_MODE, mode)
        startActivity(intent)
    }
}