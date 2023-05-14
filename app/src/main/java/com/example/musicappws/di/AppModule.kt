package com.example.musicappws.di

import android.app.Application
import androidx.room.Room
import com.example.musicappws.data.dataSource.MusicDatabase
import com.example.musicappws.data.repository.FavMusicRepositoryImpl
import com.example.musicappws.data.repository.MusicRepository
import com.example.musicappws.data.service.MusicService
import com.example.musicappws.domain.repository.FavouriteMusicRepository
import com.example.musicappws.domain.useCase.DeleteFavMusic
import com.example.musicappws.domain.useCase.GetFavMusicList
import com.example.musicappws.domain.useCase.MusicUseCase
import com.example.musicappws.domain.useCase.SaveFavMusic
import com.example.musicappws.domain.util.Constants.MUSIC_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMusicDatabase(app: Application) : MusicDatabase{
        return Room.databaseBuilder(
            app,
            MusicDatabase::class.java,
            MusicDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavouriteMusicRepository(db: MusicDatabase): FavouriteMusicRepository {
        return FavMusicRepositoryImpl(db.musicDao)
    }

    @Provides
    @Singleton
    fun provideMusicUseCase(repo: FavouriteMusicRepository): MusicUseCase{
        return MusicUseCase(
            getFavMusicList = GetFavMusicList(repo),
            saveFavMusic = SaveFavMusic(repo),
            deleteFavMusic = DeleteFavMusic(repo)
        )
    }

    @Singleton
    @Provides
    fun provideMusicRepository(
        api: MusicService
    ) = MusicRepository(api)

    @Singleton
    @Provides
    fun provideMusicApi(): MusicService{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MUSIC_BASE_URL)
            .build()
            .create(MusicService::class.java)
    }
}