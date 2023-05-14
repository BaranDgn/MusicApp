package com.example.musicappws.data.model

data class AlbumDetailItem(
    val id: Int,
    val title : String,
    val duration: Int,
    val preview: String,
    val artist: ArtistInfo
)

data class ArtistInfo(
    val id: Int,
    val name: String
)
