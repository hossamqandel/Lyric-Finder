package com.android.lyricfinder.feature_lyric_detail.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.android.lyricfinder.feature_base.data.remote.LyricFinderService
import com.android.lyricfinder.feature_lyric_detail.data.local.DetailDao
import com.android.lyricfinder.feature_lyric_detail.data.local.entity.DetailEntity
import com.android.lyricfinder.feature_lyric_detail.domain.repository.IDetailRepository
import com.android.lyricfinder.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
 private val api: LyricFinderService,
 private val dao: DetailDao
): IDetailRepository {

    override fun getLyricBySongId(songId: Int): Flow<Resource<DetailEntity>> = flow {
        emit(Resource.Loading())
        val lastSavedResult = dao.getLyricBySongId(songId)

        delay(1000L)
        try {
            val remoteResult = api.getDetail(songId)
            dao.insertSongLyric(remoteResult.response.lyrics.toDetail().toDetailEntity())

        }catch (e: IOException){
            Log.e(TAG, "getLyricBySongId: $e")
            emit(Resource.Error(
                message = "Your are currently offline",
                data = lastSavedResult ))
        }catch (e: HttpException){
            Log.e(TAG, "getLyricBySongId: $e")
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = lastSavedResult ))
        }

        val lastInsertedResult = dao.getLyricBySongId(songId)
        emit(Resource.Success(data = lastInsertedResult))
    }
}