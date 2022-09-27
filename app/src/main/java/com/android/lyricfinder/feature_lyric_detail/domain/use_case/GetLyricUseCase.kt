package com.android.lyricfinder.feature_lyric_detail.domain.use_case

import com.android.lyricfinder.feature_lyric_detail.data.local.entity.DetailEntity
import com.android.lyricfinder.feature_lyric_detail.domain.repository.IDetailRepository
import com.android.lyricfinder.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class GetLyricUseCase @Inject constructor(
  private val repo: IDetailRepository
){

    operator fun invoke(songId: Int): Flow<Resource<DetailEntity>> {
        if (songId.toString().isBlank()){
            return flow {  }
        }

        return repo.getLyricBySongId(songId)
    }

}