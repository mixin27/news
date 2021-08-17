package com.norm.news.ui.fragments.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.norm.news.R
import com.norm.news.adapters.NewsAdapter
import com.norm.news.databinding.FragmentNewsBinding
import com.norm.news.util.NetworkListener
import com.norm.news.util.NetworkResult
import com.norm.news.util.observeOnce
import com.norm.news.viewmodels.MainViewModel
import com.norm.news.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val mainViewMode: MainViewModel by viewModels()
    private val newsViewModel: NewsViewModel by viewModels()
    private val mAdapter by lazy { NewsAdapter() }

    private lateinit var networkListener: NetworkListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewMode

        setupRecyclerView()
        loadNews()

        // network listener
        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", "$status")
                    newsViewModel.networkStatus = status
                    newsViewModel.showNetworkStatus()
                    loadNews()
                }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.adapter = mAdapter
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    /**
     * Load news data.
     */
    private fun loadNews() {
        lifecycleScope.launch {
            mainViewMode.readNews.observeOnce(viewLifecycleOwner, { rows ->
                if (rows.isNotEmpty()) {
                    Log.d("NewsFragment", "readNews() called!")

                    mAdapter.setData(rows[0].news)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    /**
     * If news didn't cache, request data from api.
     */
    private fun requestApiData() {
        Log.d("NewsFragment", "requestApiData() called!")

        mainViewMode.getNews(newsViewModel.applyQueries())
        mainViewMode.newsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { news ->
                        mAdapter.setData(news)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewMode.readNews.observeOnce(viewLifecycleOwner, { rows ->
                if (rows.isNotEmpty()) {
                    mAdapter.setData(rows[0].news)
                }
            })
        }
    }

    /** show shimmer recycler effect */
    private fun showShimmerEffect() {
        binding.newsRecyclerView.showShimmer()
    }

    /** hide shimmer recycler effect */
    private fun hideShimmerEffect() {
        binding.newsRecyclerView.hideShimmer()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        // To avoid memory leak
        _binding = null
    }

}