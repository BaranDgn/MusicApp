package com.example.musicappws.domain.useCase

import com.example.musicappws.data.model.Music
import com.example.musicappws.domain.repository.FavouriteMusicRepository

class DeleteFavMusic(
    private val repo : FavouriteMusicRepository
) {
    suspend operator fun invoke(music: Music){
        repo.deleteMusic(music)
    }
}