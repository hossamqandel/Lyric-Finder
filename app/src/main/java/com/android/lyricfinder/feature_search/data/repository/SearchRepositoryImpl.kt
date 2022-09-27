package com.android.lyricfinder.feature_search.data.repository

import android.content.ContentValues
import android.util.Log
import com.android.lyricfinder.feature_base.data.remote.LyricFinderService
import com.android.lyricfinder.feature_search.data.local.SearchDao
import com.android.lyricfinder.feature_search.data.local.entity.SearchEntity
import com.android.lyricfinder.feature_search.domain.repository.ISearchRepository
import com.android.lyricfinder.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: LyricFinderService,
    private val dao: SearchDao
): ISearchRepository {

    /**
     * @param this param refers to search query by Singer Name
     * @return multiple songs of specific singer
     * @see delay here are used to make a period timer between old and newest server call
     * */

    override fun searchForSongsBySingerName(artistName: String): Flow<Resource<List<SearchEntity>>> = flow {
        emit(Resource.Loading())

        val oldLocalResult = dao.getAllSongsBySingerName(artistName)
        emit(Resource.Loading(data = oldLocalResult))

        delay(1500L)

        try {
            val remoteResult = api.searchForSongOrArtist(artistName).response.toSearchEntities()
            dao.insertSongs(remoteResult)
        }catch (e: HttpException){
            Log.e(ContentValues.TAG, "searchAboutSongOrArtist: $e" )
            emit(Resource.Error(data = oldLocalResult, message = "Oops, something went wrong!"))
        } catch (e: IOException){
            Log.e(ContentValues.TAG, "searchAboutSongOrArtist: $e" )
            emit(Resource.Success(data = oldLocalResult))

        }

        val newLocalResult = dao.getAllSongsBySingerName(artistName)
        emit(Resource.Success(data = newLocalResult))

    }

}