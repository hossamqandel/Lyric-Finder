package com.android.lyricfinder.feature_search.domain.repository

import com.android.lyricfinder.feature_search.data.remote.dto.SearchDto
import com.android.lyricfinder.feature_search.domain.model.Search

interface ISearchRepository {

    suspend fun searchAboutSongOrArtist(songTitle: String): SearchDto
}