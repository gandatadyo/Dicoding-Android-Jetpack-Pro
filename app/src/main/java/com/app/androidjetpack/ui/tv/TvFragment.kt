package com.app.androidjetpack.ui.tv

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.androidjetpack.databinding.FragmentTvBinding
import com.app.androidjetpack.ui.detail.DetailItemActivity
import com.app.androidjetpack.viewmodel.ViewModelFactory
import com.app.androidjetpack.vo.Status

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
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this,factory)[TvViewModel::class.java]

            val itemAdapter = TvAdapter { itemId: String, mode: String ->showDetail(itemId,mode) }
            fragmentTvBinding.loadingView.visibility = View.VISIBLE
            viewModel.getTV().observe(requireActivity(), { tvs ->
                if (tvs != null) {
                    when (tvs.status) {
                        Status.LOADING -> fragmentTvBinding.loadingView.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            fragmentTvBinding.loadingView.visibility = View.GONE
//                            itemAdapter.setTvs(tvs.data)
//                            itemAdapter.notifyDataSetChanged()
                            itemAdapter.submitList(tvs.data)
                        }
                        Status.ERROR -> {
                            fragmentTvBinding.loadingView.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })

            with(fragmentTvBinding.rvTv) {
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