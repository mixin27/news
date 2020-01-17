package com.norm.news.ui.source


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.norm.news.R
import com.norm.news.databinding.FragmentNewsSourceBinding

/**
 * A simple [Fragment] subclass.
 */
class NewsSourceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = NewsSourceViewModelFactory(application)
        val newsSourceViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NewsSourceViewModel::class.java)
        binding.newsSourceViewModel = newsSourceViewModel

        binding.rvSourceLists.adapter = NewsSourceAdapter(NewsSourceAdapter.OnClickListener {
            newsSourceViewModel.displayNewsSourceDetails(it)
        })

        newsSourceViewModel.navigateToSelectedItem.observe(this, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(NewsSourceFragmentDirections.actionNewsSourceFragmentToNewsFragment(it.id))
                newsSourceViewModel.displayNewsSourceDetailsComplete()
            }
        })

        return binding.root
    }
}
