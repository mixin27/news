package com.norm.news.ui.news


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.norm.news.R
import com.norm.news.databinding.FragmentNewsBinding
import com.norm.news.utils.IMMLeaks
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // for input method memory leaks.
        IMMLeaks.fixFocusedViewLeak(requireNotNull(this.activity).application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_news,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val arguments = arguments?.let { NewsFragmentArgs.fromBundle(it) }
        val newsViewModelFactory = NewsViewModelFactory(application, arguments!!.selectedSourceId)
        val newsViewModel =
            ViewModelProviders.of(this, newsViewModelFactory).get(NewsViewModel::class.java)
        binding.newsViewModel = newsViewModel

        binding.rvNewsLists.adapter = ArticleAdapter(ArticleAdapter.OnClickListener {
            newsViewModel.displayNewsSourceDetails(it)
        })

        newsViewModel.navigateToSelectedItem.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(it)
                )
                newsViewModel.displayNewsSourceDetailsComplete()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
