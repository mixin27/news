package com.norm.news.ui.source

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.norm.news.R
import com.norm.news.databinding.FragmentNewsSourceBinding
import com.norm.news.utils.IMMLeaks
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class NewsSourceFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // setHasOptionsMenu(true)
    // for input method memory leaks.
    IMMLeaks.fixFocusedViewLeak(requireNotNull(this.activity).application)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding: FragmentNewsSourceBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_news_source,
        container,
        false
    )
    // Specify the current activity as the lifecycle owner of the binding.
    // This is necessary so that the binding can observe LiveData updates.
    binding.lifecycleOwner = viewLifecycleOwner

    val application = requireNotNull(this.activity).application
    val viewModelFactory = NewsSourceViewModelFactory(application)
    val newsSourceViewModel =
      ViewModelProviders.of(this, viewModelFactory)
          .get(NewsSourceViewModel::class.java)
    binding.newsSourceViewModel = newsSourceViewModel

    binding.rvSourceLists.adapter = NewsSourceAdapter(NewsSourceAdapter.OnClickListener {
      newsSourceViewModel.displayNewsSourceDetails(it)
    })

    newsSourceViewModel.navigateToSelectedItem.observe(viewLifecycleOwner, Observer {
      if (it != null) {
        this.findNavController()
            .navigate(NewsSourceFragmentDirections.actionNewsSourceFragmentToNewsFragment(it.id))
        newsSourceViewModel.displayNewsSourceDetailsComplete()
      }
    })

    return binding.root
  }

//  override fun onCreateOptionsMenu(
//    menu: Menu,
//    inflater: MenuInflater
//  ) {
//    inflater.inflate(R.menu.search_menu, menu)
//    // Associate searchable configuration with the SearchView
//    val searchManager = context!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//    val searchView = menu.findItem(R.id.search).actionView as SearchView
//    searchView.apply {
//      setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
//    }
//    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//      override fun onQueryTextChange(newText: String?): Boolean {
//        Timber.d("Query = $newText")
//        return true
//      }
//
//      override fun onQueryTextSubmit(query: String?): Boolean {
//        Timber.d("Submit Query = $query")
//        return true
//      }
//    })
//
//    super.onCreateOptionsMenu(menu, inflater)
//  }
}
