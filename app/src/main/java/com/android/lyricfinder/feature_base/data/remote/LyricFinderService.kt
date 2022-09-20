package com.android.lyricfinder.feature_base.data.remote

import com.android.lyricfinder.feature_lyric_detail.data.remote.DetailDto
import com.android.lyricfinder.feature_search.data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val PER_PAGE = "10"
private const val PAGE = "1"

interface LyricFinderService {

    @GET("songs/{songId}/lyrics")
    suspend fun getDetail(
        @Path("songId") songId: String
    ): DetailDto


    @GET("search")
    suspend fun searchForSongOrArtist(
        @Query("q") q: String,
        @Query("per_page") per_page: String = PER_PAGE,
        @Query("page") page: String = PAGE
    ): SearchDto


}