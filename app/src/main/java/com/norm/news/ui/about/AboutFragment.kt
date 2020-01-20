package com.norm.news.ui.about


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.norm.news.R
import com.norm.news.databinding.FragmentAboutBinding
import com.norm.news.utils.IMMLeaks

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for input method memory leaks.
        IMMLeaks.fixFocusedViewLeak(requireNotNull(this.activity).application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAboutBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_about,
            container,
            false
        )
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val aboutViewModelFactory = AboutViewModelFactory(application)
        val aboutViewModel =
            ViewModelProviders.of(this, aboutViewModelFactory).get(AboutViewModel::class.java)
        binding.viewModel = aboutViewModel

        return binding.root
    }


}
