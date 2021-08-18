package com.norm.news.ui.fragments.news

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
class NewsFragment : Fragment(), SearchView.OnQueryTextListener,
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<NewsFragmentArgs>()

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
        binding.newsViewModel = newsViewModel

        setHasOptionsMenu(true)

        setupRecyclerView()
        loadNews()

        binding.newsSwipeRefreshLayout.setOnRefreshListener(this)

        newsViewModel.readBackOnline.observe(viewLifecycleOwner, {
            newsViewModel.backOnline = it
        })

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

        binding.filterNewsFab.setOnClickListener {
            if (newsViewModel.networkStatus) {
                findNavController()
                    .navigate(R.id.action_newsFragment_to_newsFilterBottomSheetFragment)
            } else {
                newsViewModel.showNetworkStatus()
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.adapter = mAdapter
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_menu, menu)

        val search = menu.findItem(R.id.search_news_menu)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchNewsFromApi(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    /**
     * Load news data.
     */
    private fun loadNews() {
        lifecycleScope.launch {
            mainViewMode.readNews.observeOnce(viewLifecycleOwner, { rows ->
                if (rows.isNotEmpty() && !args.backFromFilterSheet) {
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
                    hideSwipeRefresh()
                    response.data?.let { news ->
                        mAdapter.setData(news)
                    }

                    // save filter types to datastore
                    // newsViewModel.saveFilterTypes()
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    hideSwipeRefresh()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                    showSwipeRefresh()
                }
            }
        })
    }

    private fun searchNewsFromApi(query: String) {
        showShimmerEffect()
        mainViewMode.searchedNews(newsViewModel.applySearchQueries(query))
        mainViewMode.searchedNewsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.setData(it)
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

    override fun onRefresh() {
        requestApiData()
    }

    private fun showSwipeRefresh() {
        binding.newsSwipeRefreshLayout.isRefreshing = true
    }

    private fun hideSwipeRefresh() {
        binding.newsSwipeRefreshLayout.isRefreshing = false
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