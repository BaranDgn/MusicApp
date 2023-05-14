package com.example.musicappws.domain.useCase

import com.example.musicappws.data.model.Music
import com.example.musicappws.domain.repository.FavouriteMusicRepository
import com.example.musicappws.domain.util.MusicOrder
import com.example.musicappws.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavMusicList(
    private val repo: FavouriteMusicRepository
) {
    operator fun invoke(
        musicOrder: MusicOrder = MusicOrder.Title(OrderType.Ascending)
    ): Flow<List<Music>> {
      return repo.getMusic().map { music ->
          music.sortedBy { it.title.lowercase() }
      }
    }
}