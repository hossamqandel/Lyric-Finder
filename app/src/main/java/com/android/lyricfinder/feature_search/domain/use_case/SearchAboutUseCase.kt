package com.android.lyricfinder.feature_search.domain.use_case

import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import com.android.lyricfinder.feature_search.domain.repository.ISearchRepository
import com.android.lyricfinder.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchAboutUseCase @Inject constructor(
    private val repo: ISearchRepository,
) {

    operator fun invoke(artistName: String): Flow<Resource<List<SearchEntity>>> {

        if (artistName.isBlank()){
            return flow { }
        }

        return repo.searchForSongsBySingerName(artistName)
    }
}