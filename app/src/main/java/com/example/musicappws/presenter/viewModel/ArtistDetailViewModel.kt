package com.example.musicappws.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.example.musicappws.Resource
import com.example.musicappws.data.model.ArtistDetail

import com.example.musicappws.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val repo : MusicRepository
) :ViewModel(){

    suspend fun loadSongOfArtist(artistId: Int): Resource<ArtistDetail>{
        return repo.loadArtistDetail(artist_id = artistId)
    }
}