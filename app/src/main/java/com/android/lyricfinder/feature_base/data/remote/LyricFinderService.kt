package com.android.lyricfinder.feature_base.data.remote

import com.android.lyricfinder.feature_lyric_detail.data.remote.DetailDto
import com.android.lyricfinder.feature_search.data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LyricFinderService {

    @GET("songs/{songId}/lyrics")
    suspend fun getDetail(
        @Path("songId") songId: String
    ): DetailDto


    @GET("search")
    suspend fun searchForSongOrArtist(
        @Query("q") q: String,
        @Query("per_page") per_page: String = "10",
        @Query("page") page: String = "1"
    ): SearchDto


}