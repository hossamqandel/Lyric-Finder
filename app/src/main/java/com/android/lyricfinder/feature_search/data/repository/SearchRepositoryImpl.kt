package com.android.lyricfinder.feature_search.data.repository

import com.android.lyricfinder.feature_base.data.remote.LyricFinderService
import com.android.lyricfinder.feature_search.data.remote.dto.SearchDto
import com.android.lyricfinder.feature_search.domain.repository.ISearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: LyricFinderService,
): ISearchRepository {

    override suspend fun searchAboutSongOrArtist(songTitle: String): SearchDto {
        val result = api.searchForSongOrArtist(songTitle)
        return result
    }


}