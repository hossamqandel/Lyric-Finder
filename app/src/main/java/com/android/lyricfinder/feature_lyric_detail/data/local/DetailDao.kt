package com.android.lyricfinder.feature_lyric_detail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.lyricfinder.feature_lyric_detail.data.local.entity.DetailEntity

@Dao
interface DetailDao {

    @Insert
    suspend fun insertSongLyric(detailEntity: DetailEntity)

    @Query("SELECT * FROM DetailEntity WHERE id = :id")
    fun getLyricById(id: Int): DetailEntity
}