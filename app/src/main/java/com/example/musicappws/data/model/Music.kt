package com.example.musicappws.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Music(

    @PrimaryKey val id: Int? = null,

    val title : String,

    val duration: Int,

    val heartButton : Boolean,

    val picOfSong: String,
)