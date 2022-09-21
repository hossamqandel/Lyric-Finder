package com.android.lyricfinder.feature_search.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class SearchEntity(
    val imageUrl: String? = null,
    val songTitle: String = "".lowercase(),
    val singerName: String? = null,
    @PrimaryKey(autoGenerate = false)
    val songId: Int? = null
)
