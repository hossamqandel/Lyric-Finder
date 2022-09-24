package com.android.lyricfinder.feature_lyric_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.lyricfinder.feature_lyric_detail.domain.use_case.GetLyricUseCase
import com.android.lyricfinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getLyricUseCase: GetLyricUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _errorChannel = Channel<String>()
    val errorChannel = _errorChannel.receiveAsFlow()

    fun getDetail(detailEvent: DetailEvent) {
        when (detailEvent) {
            is DetailEvent.SongId -> {

                getLyricUseCase.invoke(detailEvent.songId).onEach { resource ->
                    when (resource) {
                        is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                        is Resource.Success -> _state.value =
                            state.value.copy(isLoading = false, lyric = resource.data)
                        is Resource.Error -> { _state.value = state.value.copy(
                                isLoading = false,
                                lyric = resource.data)
                            _errorChannel.send(resource.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }


}