package com.android.lyricfinder.di

import com.android.lyricfinder.feature_base.data.local.LyricFinderDatabase
import com.android.lyricfinder.feature_search.domain.repository.ISearchRepository
import com.android.lyricfinder.feature_search.domain.use_case.SearchAboutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSearchAboutUseCase(repo: ISearchRepository, database: LyricFinderDatabase): SearchAboutUseCase {
        return SearchAboutUseCase(repo, database)
    }
}