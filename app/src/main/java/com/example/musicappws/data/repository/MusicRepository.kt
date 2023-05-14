package com.example.musicappws.data.repository

import com.example.musicappws.Resource
import com.example.musicappws.data.model.AlbumDetail
import com.example.musicappws.data.model.Artist
import com.example.musicappws.data.model.ArtistDetail
import com.example.musicappws.data.model.Category
import com.example.musicappws.data.service.MusicService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class MusicRepository@Inject constructor(
    private val api : MusicService
) {

    suspend fun loadCategories(): Resource<Category>{
        val responseOfCategory = try{
            api.getCategory()
        }catch (e: Exception){
            return Resource.Error("error")
        }
        return Resource.Success(responseOfCategory)
    }

    suspend fun loadArtist(artist_id : Int): Resource<Artist>{
        val reponseOfArtist = try{
            api.getArtist(artist_id)
        }catch (e: Exception){
            return Resource.Error("Error at Artist")
        }
        return Resource.Success(reponseOfArtist)
    }

    suspend fun loadArtistDetail(artist_id: Int): Resource<ArtistDetail>{
        val responseOfArtistDetail = try{
            api.getArtistId(artist_id)
        }catch (e: Exception){
            return Resource.Error("Error at Artist Id")
        }
        return Resource.Success(responseOfArtistDetail)
    }

    suspend fun loadAlbumDetail(albumId: Int): Resource<AlbumDetail>{
        val responseOfAlbum = try{
            api.getAlbumById(albumId)

        }catch(e: Exception){
            return Resource.Error("Error at Album Detail")
        }
        return Resource.Success(responseOfAlbum)
    }


}