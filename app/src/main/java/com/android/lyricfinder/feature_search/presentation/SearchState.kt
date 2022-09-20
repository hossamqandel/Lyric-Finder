package com.android.lyricfinder.feature_search.presentation

import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity

data class SearchState(
    val data: List<SearchEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
