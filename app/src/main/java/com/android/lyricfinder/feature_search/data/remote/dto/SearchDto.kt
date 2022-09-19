package com.android.lyricfinder.feature_search.data.remote.dto

import com.android.lyricfinder.feature_search.domain.model.Search

data class SearchDto(
    val meta: Meta,
    val response: Response
)

data class Meta(
    val status: Int
)

data class Response(
    val hits: List<Hit>
)

data class Hit(
    val highlights: List<Any>,
    val index: String,
    val result: Result,
    val type: String
)

data class Result(
    val _type: String,
    val annotation_count: Int,
    val api_path: String,
    val artist_names: String,
    val featured_artists: List<FeaturedArtist>,
    val full_title: String,
    val header_image_thumbnail_url: String,
    val header_image_url: String,
    val id: Int,
    val instrumental: Boolean,
    val language: String,
    val lyrics_owner_id: Int,
    val lyrics_state: String,
    val lyrics_updated_at: Int,
    val path: String,
    val primary_artist: PrimaryArtist,
    val pyongs_count: Int,
    val relationships_index_url: String,
    val release_date_components: ReleaseDateComponents,
    val release_date_for_display: String,
    val song_art_image_thumbnail_url: String,
    val song_art_image_url: String,
    val stats: Stats,
    val title: String,
    val title_with_featured: String,
    val updated_by_human_at: Int,
    val url: String
){
    fun toSearch(): Search {
        return Search(
            imageUrl = song_art_image_thumbnail_url,
            songTitle = title,
            artistName = artist_names,
            songId = id
        )
    }
}

data class FeaturedArtist(
    val _type: String,
    val api_path: String,
    val header_image_url: String,
    val id: Int,
    val image_url: String,
    val index_character: String,
    val iq: Int,
    val is_meme_verified: Boolean,
    val is_verified: Boolean,
    val name: String,
    val slug: String,
    val url: String
)

data class PrimaryArtist(
    val _type: String,
    val api_path: String,
    val header_image_url: String,
    val id: Int,
    val image_url: String,
    val index_character: String,
    val iq: Int,
    val is_meme_verified: Boolean,
    val is_verified: Boolean,
    val name: String,
    val slug: String,
    val url: String
)

data class ReleaseDateComponents(
    val day: Int,
    val month: Int,
    val year: Int
)

data class Stats(
    val hot: Boolean,
    val pageviews: Int,
    val unreviewed_annotations: Int
)