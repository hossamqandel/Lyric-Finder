package com.android.lyricfinder.feature_lyric_detail.presentation

sealed class DetailEvent {
    data class SongId(val songId: Int): DetailEvent()
}
