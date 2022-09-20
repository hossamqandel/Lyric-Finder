package com.android.lyricfinder.feature_search.domain.use_case

import android.content.ContentValues.TAG
import android.util.Log
import com.android.lyricfinder.feature_base.data.local.LyricFinderDatabase
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import com.android.lyricfinder.feature_search.domain.repository.ISearchRepository
import com.android.lyricfinder.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchAboutUseCase @Inject constructor(
    private val repo: ISearchRepository,
    private val database: LyricFinderDatabase
) {

    operator fun invoke(artistName: String): Flow<Resource<List<SearchEntity>>> = flow {

        if (artistName.isBlank()) { return@flow }

        emit(Resource.Loading())
        var localResult: List<SearchEntity>

        withContext(Dispatchers.IO){
            localResult = database.searchDao.getAllSongsBySongTitle(artistName)
        }


        try {
            delay(1500L)
            withContext(Dispatchers.IO){
                val remoteResult = repo.searchAboutSongOrArtist(artistName).response.toSearchEntities()
                database.searchDao.insertSongs(remoteResult)
            }
        }catch (e: Exception){
            Log.e(TAG, "invoke: $e" )
            emit(Resource.Error(
                data = localResult,
                message = "Unknown error.. please check your internet connection and try again"))
            return@flow
        }
        emit(Resource.Success(data = localResult))
    }
}