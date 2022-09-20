package com.android.lyricfinder.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.lyricfinder.feature_search.domain.use_case.SearchAboutUseCase
import com.android.lyricfinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchAboutUseCase: SearchAboutUseCase
): ViewModel() {

    private val _state= MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    init {
        searchAbout("Alan Walker")
    }

    fun searchAbout(songTitle: String){
        searchAboutUseCase.invoke(songTitle).onEach {
            when(it){
                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)

                is Resource.Success -> _state.value = state.value.copy(data = it.data ?: emptyList(), isLoading = false)

                is Resource.Error -> _state.value = state.value.copy(isLoading = false,
                    errorMessage = "Unknown error.. please check your internet connection and try again")
            }
        }.launchIn(viewModelScope)
    }
}