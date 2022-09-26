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
import androidx.navigation.fragment.findNavController
import com.android.lyricfinder.databinding.FragmentSearchBinding
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import com.android.lyricfinder.utils.SharedPrefs
import com.android.lyricfinder.utils.disableNightMode
import com.android.lyricfinder.utils.enableNightMode
import com.android.lyricfinder.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy { SearchAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClicks()
        stateCollector()
        channelCollector()
        setSwitchButtonStateBasedOnLastThemeSelected()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClicks(){
        binding.apply {
            btnSearch.setOnClickListener {
                viewModel.searchAbout(SearchEvent.EnteredTitle(etTitle.text.toString()))
            }

            searchAdapter.setOnItemClick(object : ICustomClick{
                override fun <T> onItemClick(vararg data: T) {
                    Log.e(TAG, "onItemClick: ${data[0]}", )
                    val action = SearchFragmentDirections.
                    actionSearchFragmentToDetailFragment(
                        data[0].toString().toInt())
                    findNavController().navigate(action)
                }
            })


            btnSwitchTheme.setOnCheckedChangeListener { compoundButton, isSwitched ->
                changeAppThemeModeBasedOnSwitchButtonStatus(isSwitched)
            }
        }

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

    private fun channelCollector(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.errorChannel.collectLatest { errorMessage ->
                    showErrorMessage(errorMessage)
                }
            }
        }
    }

    private fun progressVisibility(isVisible: Boolean){
        binding.progressBar.isVisible = isVisible
    }

    private fun setupRecyclerView(data: List<SearchEntity>){
        binding.rvSongs.apply {
            searchAdapter.setSearchData(data)
            this.adapter = searchAdapter
            data.forEach { Log.e(TAG, "setupRecyclerView: ${it.songTitle}") }
        }
    }

    private fun showErrorMessage(content: String){
        showSnackBar(content)
    }

    private fun setSwitchButtonStateBasedOnLastThemeSelected(){
        binding.btnSwitchTheme.isChecked = SharedPrefs.getNightMode()
    }


    private fun changeAppThemeModeBasedOnSwitchButtonStatus(isSwitched: Boolean){
        if (isSwitched){
            enableNightMode()
            SharedPrefs.setNightMode(true)
        } else {
            disableNightMode()
            SharedPrefs.setNightMode(false)

        }
    }




}