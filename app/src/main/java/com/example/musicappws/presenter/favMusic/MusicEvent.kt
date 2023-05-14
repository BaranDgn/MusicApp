package com.example.musicappws.presenter.favMusic

import com.example.musicappws.data.model.Music
import com.example.musicappws.domain.util.MusicOrder

sealed class MusicEvent{

    data class Order(val musicOrder: MusicOrder) : MusicEvent()

    data class deleteMusic(val music: Music) : MusicEvent()

    object RestoreMusic: MusicEvent()

    object SaveNote: MusicEvent()

}
