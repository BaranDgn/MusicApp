package com.example.musicappws.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicappws.data.model.Music

@Database(
    entities = [Music::class],
    version = 1
)
abstract class MusicDatabase : RoomDatabase(){
    abstract val musicDao: FavouriteMusicDao

    companion object{
        const val DATABASE_NAME = "music_db"
    }
}