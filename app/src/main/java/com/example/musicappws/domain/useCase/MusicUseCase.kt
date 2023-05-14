package com.example.musicappws.domain.useCase

data class MusicUseCase(
    val getFavMusicList: GetFavMusicList,
    val saveFavMusic: SaveFavMusic,
    val deleteFavMusic: DeleteFavMusic
)
