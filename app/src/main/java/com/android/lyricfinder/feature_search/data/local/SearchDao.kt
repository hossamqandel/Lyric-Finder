package com.android.lyricfinder.feature_search.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import kotlinx.coroutines.Deferred

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(searchEntities: List<SearchEntity>)

    @Query("SELECT * FROM SearchEntity WHERE singerName LIKE :singerName")
    suspend fun getAllSongsBySingerName(singerName: String): List<SearchEntity>
}