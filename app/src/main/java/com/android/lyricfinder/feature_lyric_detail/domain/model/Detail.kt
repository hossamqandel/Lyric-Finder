package com.android.lyricfinder.feature_lyric_detail.domain.model

import com.android.lyricfinder.feature_lyric_detail.data.local.entity.DetailEntity

data class Detail(
    var lyrics: String? = null,
    val songTitle: String? = null,
    val songId: Int? = null
){

    fun toDetailEntity(): DetailEntity {
        return DetailEntity(
            lyrics = lyrics,
            songTitle = songTitle,
            songId = songId
        )
    }
}
