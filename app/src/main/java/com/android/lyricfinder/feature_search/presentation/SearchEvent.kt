package com.android.lyricfinder.feature_search.presentation

sealed class SearchEvent {
    data class EnteredTitle(val title: String = ""): SearchEvent()
}
