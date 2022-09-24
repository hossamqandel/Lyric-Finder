package com.android.lyricfinder.di

import com.android.lyricfinder.feature_base.data.local.LyricFinderDatabase
import com.android.lyricfinder.feature_base.data.remote.LyricFinderService
import com.android.lyricfinder.feature_lyric_detail.data.repository.DetailRepositoryImpl
import com.android.lyricfinder.feature_lyric_detail.domain.repository.IDetailRepository
import com.android.lyricfinder.feature_search.data.repository.SearchRepositoryImpl
import com.android.lyricfinder.feature_search.domain.repository.ISearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSearchRepo(api: LyricFinderService, database: LyricFinderDatabase): ISearchRepository {
        return SearchRepositoryImpl(api, database.searchDao)
    }

    @Provides
    fun provideDetailRepo(api: LyricFinderService, database: LyricFinderDatabase): IDetailRepository {
        return DetailRepositoryImpl(api, database.detailDao)
    }
}