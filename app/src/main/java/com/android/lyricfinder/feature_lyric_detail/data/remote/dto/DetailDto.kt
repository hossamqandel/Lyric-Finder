package com.android.lyricfinder.feature_lyric_detail.data.remote

import com.android.lyricfinder.feature_lyric_detail.domain.model.Detail
import com.google.gson.annotations.SerializedName

data class DetailDto(
    val meta: Meta,
    val response: Response
)

data class Meta(
    val status: Int
)

data class Response(
    val lyrics: Lyrics
)

data class Lyrics(
    val _type: String,
    val api_path: String,
    val lyrics: LyricsX,
    val path: String,
    val song_id: Int,
    val tracking_data: TrackingData
){

    fun toDetail(): Detail {
        return Detail(
            lyrics = lyrics.body.html,
            songTitle = "Lyrics for ${tracking_data.title}"
        )
    }
}

data class LyricsX(
    val body: Body
)

data class TrackingData(
    val annotatable_id: Int,
    val annotatable_type: String,
    val cohort_ids: List<Int>,
    val comment_count: Int,
    val created_at: String,
    val created_month: String,
    val created_year: Int,
    val featured_video: Boolean,
    val has_apple_match: Boolean,
    val has_description: Boolean,
    val has_featured_annotation: Boolean,
    val has_recirculated_articles: Boolean,
    val has_recommendations: Boolean,
    @SerializedName("has_translation_q&a")
    val has_translation_qa: Boolean,
    val has_verified_callout: Boolean,
    val has_youtube_url: Boolean,
    val hot: Boolean,
    val lyrics_language: String,
    @SerializedName("music?")
    val music: Boolean,
    val nrm_target_date: String,
    val nrm_tier: Int,
    val platform_variant: String,
    val primary_album: String,
    val primary_album_id: Int,
    val primary_artist: String,
    val primary_artist_id: Int,
    val primary_tag: String,
    val primary_tag_id: Int,
    val release_date: String,
    val song_id: Int,
    val song_tier: String,
    val tag: String,
    val title: String,
    val web_interstitial_variant: String
)

data class Body(
    val dom: Dom,
    val html: String,
    val plain: String
)

data class Dom(
    val children: List<Any>,
    val tag: String
)