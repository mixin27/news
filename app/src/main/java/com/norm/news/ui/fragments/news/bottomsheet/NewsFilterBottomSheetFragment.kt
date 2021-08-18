package com.norm.news.ui.fragments.news.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norm.news.R
import com.norm.news.databinding.FragmentNewsFilterBottomSheetBinding
import com.norm.news.util.Constants.Companion.DEFAULT_CATEGORY
import com.norm.news.util.Constants.Companion.DEFAULT_SORT_BY
import com.norm.news.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFilterBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNewsFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by viewModels()

    private var categoryChip = DEFAULT_CATEGORY
    private var categoryChipId = 0
    private var sortByChip = DEFAULT_SORT_BY
    private var sortByChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentNewsFilterBottomSheetBinding.inflate(inflater, container, false)

        newsViewModel.readFilterTypes.asLiveData().observe(viewLifecycleOwner, { value ->
            categoryChip = value.selectedCategory
            sortByChip = value.selectedSortBy

            updateChip(value.selectedCategoryId, binding.categoryChipGroup)
            updateChip(value.selectedSortById, binding.sortByChipGroup)
        })

        binding.categoryChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedCategory = chip.text.toString()
            categoryChip = selectedCategory
            categoryChipId = checkedId
        }

        binding.sortByChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedSortBy = chip.text.toString()
            sortByChip = selectedSortBy
            sortByChipId = checkedId
        }

        binding.applyBtn.setOnClickListener {
            newsViewModel.saveFilterTypes(
                categoryChip,
                categoryChipId,
                sortByChip,
                sortByChipId
            )
            val action =
                NewsFilterBottomSheetFragmentDirections.actionNewsFilterBottomSheetFragmentToNewsFragment(
                    true
                )
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                val targetView = chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
                chipGroup.requestChildFocus(targetView, targetView)
            } catch (e: Exception) {
                Log.d("NewsFilterBottomSheetFragment", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}