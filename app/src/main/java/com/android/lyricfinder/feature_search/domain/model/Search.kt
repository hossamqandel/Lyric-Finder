package com.android.lyricfinder.feature_search.domain.model

data class Search(
    val imageUrl: String? = null,
    val songTitle: String? = null,
    val artistName: String? = null,
    val songId: Int? = null
)
