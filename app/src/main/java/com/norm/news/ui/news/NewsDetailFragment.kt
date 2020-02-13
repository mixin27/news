package com.norm.news.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.norm.news.R
import com.norm.news.databinding.FragmentNewsDetailBinding
import com.norm.news.utils.IMMLeaks

/**
 * A simple [Fragment] subclass.
 */
class NewsDetailFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // for input method memory leaks.
    IMMLeaks.fixFocusedViewLeak(requireNotNull(this.activity).application)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding: FragmentNewsDetailBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_news_detail,
        container,
        false
    )
    binding.lifecycleOwner = viewLifecycleOwner

    val application = requireNotNull(this.activity).application
    val args = arguments?.let {
      NewsDetailFragmentArgs.fromBundle(it)
    }
    val newsDetailViewModelFactory =
      NewsDetailViewModelFactory(application, args!!.selectedArticle)
    val newsDetailViewModel = ViewModelProviders.of(this, newsDetailViewModelFactory)
        .get(NewsDetailViewModel::class.java)
    binding.viewModel = newsDetailViewModel

    return binding.root
  }
}
