package com.example.musicappws.domain.repository

import com.example.musicappws.data.model.Music
import kotlinx.coroutines.flow.Flow

interface FavouriteMusicRepository {

    fun getMusic(): Flow<List<Music>>

    suspend fun getMusicById(id: Int): Music?

    suspend fun insertMusic(music: Music)

    suspend fun deleteMusic(music: Music)

}