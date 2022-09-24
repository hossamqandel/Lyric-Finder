package com.android.lyricfinder.feature_lyric_detail.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DetailEntity(
    var lyrics: String? = null,
    val songTitle: String? = null,
    @PrimaryKey(autoGenerate = false)
    val songId: Int? = null
)
