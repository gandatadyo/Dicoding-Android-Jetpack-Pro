package com.app.androidjetpack.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.androidjetpack.databinding.FragmentMovieBinding
import com.app.androidjetpack.ui.detail.DetailItemActivity
import com.app.androidjetpack.viewmodel.ViewModelFactory
import com.app.androidjetpack.vo.Status

class MovieFragment : Fragment() {
    private lateinit var fragmentMovieBinding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        fragmentMovieBinding.loadingView.visibility = View.GONE
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this,factory)[MovieViewModel::class.java]

            val itemAdapter = MovieAdapter { itemId: String, mode: String ->showDetail(itemId,mode) }
            fragmentMovieBinding.loadingView.visibility = View.VISIBLE
            viewModel.getMovies().observe(requireActivity(), { movies ->
                if (movies != null) {
                    when (movies.status) {
                        Status.LOADING -> fragmentMovieBinding.loadingView.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            fragmentMovieBinding.loadingView.visibility = View.GONE
                            itemAdapter.submitList(movies.data)
                        }
                        Status.ERROR -> {
                            fragmentMovieBinding.loadingView.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(fragmentMovieBinding.rvMovie) {
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