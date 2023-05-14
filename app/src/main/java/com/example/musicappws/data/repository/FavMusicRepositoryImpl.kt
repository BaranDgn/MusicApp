package com.example.musicappws.data.repository

import com.example.musicappws.data.dataSource.FavouriteMusicDao
import com.example.musicappws.data.model.Music
import com.example.musicappws.domain.repository.FavouriteMusicRepository
import kotlinx.coroutines.flow.Flow

class FavMusicRepositoryImpl(
    private val dao: FavouriteMusicDao
) : FavouriteMusicRepository{
    override fun getMusic(): Flow<List<Music>> {
        return dao.getMusicList()
    }

    override suspend fun getMusicById(id: Int): Music? {
        return dao.getMusicById(id)
    }

    override suspend fun insertMusic(music: Music) {
        return dao.insertMusicItem(music)
    }

    override suspend fun deleteMusic(music: Music) {
        return dao.deleteMusic(music)
    }
}