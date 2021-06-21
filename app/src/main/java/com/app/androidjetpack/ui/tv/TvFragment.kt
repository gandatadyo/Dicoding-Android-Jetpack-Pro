package com.app.androidjetpack.ui.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.androidjetpack.databinding.FragmentTvBinding
import com.app.androidjetpack.utils.EspressoIdlingResource
import com.app.androidjetpack.viewmodel.ViewModelFactory

class TvFragment : Fragment() {
    private lateinit var fragmentTvBinding: FragmentTvBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTvBinding = FragmentTvBinding.inflate(layoutInflater, container, false)
        fragmentTvBinding.loadingView.visibility = View.GONE
        return fragmentTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(this,factory)[TvViewModel::class.java]

            val academyAdapter = TvAdapter()
            EspressoIdlingResource.increment()
            fragmentTvBinding.loadingView.visibility = View.VISIBLE
            viewModel.getTV().observe(requireActivity(), { movies ->
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    //Memberitahukan bahwa tugas sudah selesai dijalankan
                    EspressoIdlingResource.decrement()
                }
                academyAdapter.setTvs(movies)
                academyAdapter.notifyDataSetChanged()
                fragmentTvBinding.loadingView.visibility = View.GONE
            })

            with(fragmentTvBinding.rvTv) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = academyAdapter
            }
        }
    }
}