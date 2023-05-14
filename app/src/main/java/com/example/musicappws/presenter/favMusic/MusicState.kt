package com.example.musicappws.presenter.favMusic

import com.example.musicappws.data.model.Music

data class MusicState(
    val musics: List<Music> = emptyList(),
    val isHeartVisible: Boolean = true
)
