package com.norm.news.ui.fragments.sources

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.norm.news.adapters.SourcesAdapter
import com.norm.news.databinding.FragmentSourcesBinding
import com.norm.news.util.NetworkListener
import com.norm.news.util.NetworkResult
import com.norm.news.util.observeOnce
import com.norm.news.viewmodels.MainViewModel
import com.norm.news.viewmodels.SourcesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SourcesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener {

    private var _binding: FragmentSourcesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val sourcesViewModel: SourcesViewModel by viewModels()
    private val mAdapter by lazy { SourcesAdapter() }

    private lateinit var networkListener: NetworkListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.sourcesViewModel = sourcesViewModel

        setupRecyclerView()
        binding.sourcesSwipeRefreshLayout.setOnRefreshListener(this)

        sourcesViewModel.readBackOnline.observe(viewLifecycleOwner, {
            sourcesViewModel.backOnline = it
        })

        // network listener
        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", "$status")
                    sourcesViewModel.networkStatus = status
                    sourcesViewModel.showNetworkStatus()
                    loadSources()
                }
        }

        return binding.root
    }

    private fun loadSources() {
        lifecycleScope.launch {
            mainViewModel.readSources.observeOnce(viewLifecycleOwner, { rows ->
                if (rows.isNotEmpty()) {
                    mAdapter.setData(rows[0].sources)
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        mainViewModel.getSources(sourcesViewModel.applyQueries())
        mainViewModel.sourcesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideSwipeRefresh()
                    response.data?.let { sources ->
                        mAdapter.setData(sources)
                    }
                }
                is NetworkResult.Error -> {
                    hideSwipeRefresh()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showSwipeRefresh()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readSources.observe(viewLifecycleOwner, { rows ->
                if (rows.isNotEmpty()) {
                    mAdapter.setData(rows[0].sources)
                }
            })
        }
    }

    private fun setupRecyclerView() {
        binding.sourcesRecyclerView.adapter = mAdapter
        binding.sourcesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showSwipeRefresh() {
        binding.sourcesSwipeRefreshLayout.isRefreshing = true
    }

    private fun hideSwipeRefresh() {
        binding.sourcesSwipeRefreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        requestApiData()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}