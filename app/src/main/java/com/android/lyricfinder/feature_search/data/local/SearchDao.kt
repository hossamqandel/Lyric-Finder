package com.android.lyricfinder.feature_search.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(searchEntities: List<SearchEntity>)

    @Query("SELECT * FROM SearchEntity")
    fun getAllSongs(): List<SearchEntity>
}