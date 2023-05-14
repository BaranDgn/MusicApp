package com.example.musicappws.presenter.favMusic

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicappws.data.model.Music
import com.example.musicappws.domain.useCase.MusicUseCase
import com.example.musicappws.domain.util.MusicOrder
import com.example.musicappws.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavMusicViewModel@Inject constructor(
    private val musicUseCase: MusicUseCase
):ViewModel() {

    private val _state = mutableStateOf(MusicState())
    val state : State<MusicState> = _state

    private var recentlyDeletedMusic: Music?=null

    private var getMusicJob: Job?= null


    init{
        getMusic(MusicOrder.Title(OrderType.Ascending))
    }

    fun onEvent(event: MusicEvent){

        when(event){
            is MusicEvent.Order ->{
                getMusic(event.musicOrder)
            }
            is MusicEvent.deleteMusic->{
                viewModelScope.launch {
                    musicUseCase.deleteFavMusic(event.music)
                    recentlyDeletedMusic = event.music
                }
            }
            is MusicEvent.RestoreMusic ->{
                viewModelScope.launch {
                    musicUseCase.saveFavMusic(recentlyDeletedMusic ?: return@launch)
                    recentlyDeletedMusic = null
                }
            }
            else -> Unit
        }

    }

    private fun getMusic(musicOrder: MusicOrder){
        getMusicJob?.cancel()
        getMusicJob = musicUseCase.getFavMusicList(musicOrder)
            .onEach { musics->
                _state.value = state.value.copy(
                    musics = musics
                )
            }
            .launchIn(viewModelScope)
    }
}