package com.norm.news.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.norm.news.R
import com.norm.news.databinding.FragmentNewsBinding
import com.norm.news.network.EventObserver
import com.norm.news.utils.IMMLeaks

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
    inflater: LayoutInflater,
    container: ViewGroup?,
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
      ViewModelProviders.of(this, newsViewModelFactory)
          .get(NewsViewModel::class.java)
    binding.viewModel = newsViewModel

    binding.rvNewsLists.adapter = ArticleAdapter(ArticleAdapter.OnClickListener {
      newsViewModel.displayNewsSourceDetails(it)
    })

    newsViewModel.navigateToSelectedItem.observe(viewLifecycleOwner, Observer {
      if (it != null) {
        this.findNavController()
            .navigate(
                NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(it)
            )
        newsViewModel.displayNewsSourceDetailsComplete()
      }
    })

//    newsViewModel.navigateToSearchAction.observe(viewLifecycleOwner, EventObserver {
//      openSearch()
//    })

    return binding.root
  }

  private fun openSearch() {
    val action = NewsFragmentDirections.actionNewsFragmentToDestSearch()
    findNavController().navigate(action)
  }

  override fun onCreateOptionsMenu(
    menu: Menu,
    inflater: MenuInflater
  ) {
    inflater.inflate(R.menu.search_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }
}
