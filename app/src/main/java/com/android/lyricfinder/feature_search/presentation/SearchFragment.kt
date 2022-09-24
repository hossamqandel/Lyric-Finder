package com.android.lyricfinder.feature_search.presentation

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.android.lyricfinder.R
import com.android.lyricfinder.databinding.FragmentSearchBinding
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy { SearchAdapter() }

    val SHARED_PREFS_NAME = "shared_pref"
    val appSettingsPrefs = this.activity?.getSharedPreferences(SHARED_PREFS_NAME, 0)
    val sharedPrefsEdit = appSettingsPrefs?.edit()
    val isNightModeOn = appSettingsPrefs?.getBoolean("NightMode", false)


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
//        setupAppThemeSettings()
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


            btnSwitchTheme.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                    if (isChecked){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }

                }
            })
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
            data.forEach { Log.e(TAG, "setupRecyclerView: ${it.songTitle}", ) }
        }
    }

    private fun showErrorMessage(content: String){
        Snackbar.make(requireView(), content, Snackbar.LENGTH_LONG).show()
    }


    private fun changeAppTheme(){
        isNightModeOn?.let {
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit?.putBoolean("NightMode", false)
                sharedPrefsEdit?.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit?.putBoolean("NightMode", true)
                sharedPrefsEdit?.apply()
            }
        }
    }
    private fun setupAppThemeSettings(){

        isNightModeOn?.let {
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.btnSwitchTheme.text = "Disable dark mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.btnSwitchTheme.text = "Enable dark mode"
            }
        }


    }


}