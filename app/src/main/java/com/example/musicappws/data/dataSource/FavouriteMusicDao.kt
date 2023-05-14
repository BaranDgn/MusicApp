package com.example.musicappws.data.dataSource

import androidx.room.*
import com.example.musicappws.data.model.Music
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteMusicDao {

    @Query("SELECT * FROM music")
    fun getMusicList(): Flow<List<Music>>


    @Query("SELECT * FROM music WHERE id = :id")
    suspend fun getMusicById(id : Int) : Music?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusicItem(note: Music)

    @Delete
    suspend fun deleteMusic(note: Music)


}