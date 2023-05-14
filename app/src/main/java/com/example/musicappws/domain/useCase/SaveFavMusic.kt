package com.example.musicappws.domain.useCase

import com.example.musicappws.data.model.Music
import com.example.musicappws.domain.repository.FavouriteMusicRepository

class SaveFavMusic(
    private val repository: FavouriteMusicRepository
) {
    suspend operator fun invoke(music: Music){
        repository.insertMusic(music)
    }
}