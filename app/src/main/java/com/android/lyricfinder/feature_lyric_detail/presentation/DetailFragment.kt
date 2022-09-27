package com.android.lyricfinder.feature_lyric_detail.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.lyricfinder.databinding.FragmentDetailBinding
import com.android.lyricfinder.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private var songIdArgs: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        songIdArgs = DetailFragmentArgs.fromBundle(requireArguments()).songId
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        sendSongIdEvent()
        stateCollector()
        channelCollector()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun stateCollector(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collectLatest { detailState ->
                    Log.e(TAG, "onViewCreated: ${detailState.lyric}")
                    detailState.lyric?.let {
                        setupSongName(detailState.lyric.songTitle.toString())
                        setupSongLyric(detailState.lyric.lyrics.toString())
                    }
                    setupProgressLoadingState(detailState.isLoading)
                    setupUiDataStateAvailability(detailState.isNeedTry)
                }
            }
        }
    }

    private fun onClick(){
        binding.tvTryAgain?.setOnClickListener {
            sendSongIdEvent()
        }
    }
    private fun sendSongIdEvent(){
        viewModel.getDetail(DetailEvent.SongId(songIdArgs))
    }



    private fun setupSongName(songName: String){
        binding.tvSongName.text = songName
    }

    private fun setupSongLyric(songLyric: String){
        binding.tvLyric.text = songLyric
        binding.tvLyric.movementMethod = ScrollingMovementMethod()
    }

    private fun setupProgressLoadingState(isVisible: Boolean){
        binding.progressBar5.isVisible = isVisible
    }

    private fun channelCollector(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.errorChannel.collectLatest {
                    binding.tvLyric.showSnackBar(it)
                }
            }
        }
    }


    private fun setupUiDataStateAvailability(isDataAvailable: Boolean){
        binding.ivNoData?.isVisible = isDataAvailable
        binding.tvTryAgain?.isVisible = isDataAvailable
    }
}