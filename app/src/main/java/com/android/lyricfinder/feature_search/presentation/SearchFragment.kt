package com.android.lyricfinder.feature_search.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.lyricfinder.R
import com.android.lyricfinder.databinding.FragmentSearchBinding
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stateCollector()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun stateCollector(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collectLatest { searchState ->
                    progressVisibility(searchState.isLoading)
                    setupRecyclerView(searchState.data)
                }
            }
        }
    }

    private fun progressVisibility(isVisible: Boolean){
        binding.progressBar.isVisible = isVisible
    }

    private fun setupRecyclerView(data: List<SearchEntity>){
        binding.rvSongs.apply {
            data.forEach {
                Log.e(TAG, "setupRecyclerView: ${it.songTitle}", )
            }
//            this.adapter =
        }
    }


}