package com.android.lyricfinder.feature_base.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.lyricfinder.feature_lyric_detail.data.local.entity.DetailEntity
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity

private const val DATABASE_NAME = "lyric_finder"

@Database(entities = [SearchEntity::class, DetailEntity::class], version = 1)
abstract class LyricFinderDatabase : RoomDatabase(){


}