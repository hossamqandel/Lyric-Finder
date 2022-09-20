package com.android.lyricfinder.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.lyricfinder.feature_search.domain.use_case.SearchAboutUseCase
import com.android.lyricfinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchAboutUseCase: SearchAboutUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _errorChannel = Channel<String>()
    val errorChannel = _errorChannel.receiveAsFlow()

    fun searchAbout(searchEvent: SearchEvent){
        when(searchEvent){
            is SearchEvent.EnteredTitle -> {
                searchAboutUseCase.invoke(searchEvent.title).onEach {
                    when(it){
                        is Resource.Loading -> _state.value = state.value.copy(isLoading = true)

                        is Resource.Success -> _state.value = state.value.copy(data = it.data ?: emptyList(), isLoading = false)

                        is Resource.Error -> { _state.value = state.value.copy(isLoading = false,
                            data = it.data ?: emptyList())
                            _errorChannel.send(it.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }

    }
}