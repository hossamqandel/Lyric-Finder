package com.android.lyricfinder.feature_search.domain.repository

import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import com.android.lyricfinder.feature_search.data.remote.dto.SearchDto
import com.android.lyricfinder.feature_search.domain.model.Search
import com.android.lyricfinder.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {

    fun searchForSongsBySingerName(artistName: String): Flow<Resource<List<SearchEntity>>>
}