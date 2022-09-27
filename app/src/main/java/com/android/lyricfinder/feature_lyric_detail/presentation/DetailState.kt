package com.android.lyricfinder.feature_lyric_detail.presentation

import com.android.lyricfinder.feature_lyric_detail.data.local.entity.DetailEntity

data class DetailState(
    val lyric: DetailEntity? = null,
    val isLoading: Boolean = false,
    val isNeedTry: Boolean = false
)
