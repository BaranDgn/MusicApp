package com.example.musicappws.data.service

import com.example.musicappws.data.model.AlbumDetail
import com.example.musicappws.data.model.Artist
import com.example.musicappws.data.model.ArtistDetail
import com.example.musicappws.data.model.Category
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MusicService {

   //https://api.deezer.com/genre
    @GET("genre")
    suspend fun getCategory(): Category

    //https://api.deezer.com/genre/{genre_id}/artists
    @GET("genre/{genre_id}/artists")
    suspend fun getArtist(
        @Path("genre_id") genre_id : Int
    ): Artist


    //https://api.deezer.com/artist/\(artistId)/albums
    @GET("artist/{artistId}/albums")
    suspend fun getArtistId(
       @Path("artistId") artist_id : Int,
    ) : ArtistDetail



    //"https://api.deezer.com/album/\(albumId)/tracks"

    @GET("album/{albumId}/tracks")
    suspend fun getAlbumById(
        @Path("albumId") albumId : Int
    ): AlbumDetail

}