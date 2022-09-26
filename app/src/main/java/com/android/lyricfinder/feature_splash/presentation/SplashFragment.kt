package com.android.lyricfinder.feature_splash.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.lyricfinder.R
import com.android.lyricfinder.databinding.FragmentSplashBinding
import com.android.lyricfinder.utils.doNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigateToSearchFragment()
    }

    override fun onStop() {
        super.onStop()
        isFirstVisit = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToSearchFragment(){
        val action = R.id.action_splashFragment_to_searchFragment
        if (isFirstVisit){
            lifecycleScope.launch {
                delay(3000L)
                doNavigation(action)
            }
        } else {
            doNavigation(action)
        }
    }

    companion object {
        var isFirstVisit = true
    }

}