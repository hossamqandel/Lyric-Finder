package com.android.lyricfinder.feature_lyric_detail.domain.repository

import com.android.lyricfinder.feature_lyric_detail.data.local.entity.DetailEntity
import com.android.lyricfinder.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IDetailRepository {

    fun getLyricBySongId(songId: Int): Flow<Resource<DetailEntity>>
}