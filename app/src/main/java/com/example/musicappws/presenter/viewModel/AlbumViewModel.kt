package com.example.musicappws.presenter.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicappws.Resource
import com.example.musicappws.data.model.AlbumDetail
import com.example.musicappws.data.model.Music
import com.example.musicappws.data.repository.MusicRepository
import com.example.musicappws.domain.useCase.MusicUseCase
import com.example.musicappws.presenter.favMusic.MusicEvent
import com.example.musicappws.presenter.favMusic.MusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val repo : MusicRepository,
    private val musicUseCase: MusicUseCase,
    //savedStateHandle: SavedStateHandle
):ViewModel(){

    suspend fun loadOfSongOfAlbum(albumId:Int): Resource<AlbumDetail>{
        return repo.loadAlbumDetail(albumId)
    }

    private var _musicId = mutableStateOf(6)
    var musicId : State<Int> = _musicId

    private var _title = mutableStateOf("Baran marley")
    var title : State<String> = _title

    private val _duration = mutableStateOf(255)
    var duration : State<Int> = _duration

    private var _picOfSong = mutableStateOf("baran.png")
    var picOfSong : State<String> = _picOfSong

    private var _heartButton = mutableStateOf(true)
    var heartButton : State<Boolean> = _heartButton

    private val _state = mutableStateOf(MusicState())
    val state : State<MusicState> = _state



    fun updateData(
        newId: Int,
        newTitle: String,
        newDuration: Int,
        newPicOfSong: String,
        newHeartButton:Boolean)
    {
        _musicId.value = newId
        _title.value = newTitle
        _duration.value= newDuration
        _heartButton.value=newHeartButton
        _picOfSong.value=newPicOfSong
    }


    private var _eventFlow = MutableSharedFlow<UiEvent>()
    var eventFlow = _eventFlow.asSharedFlow()

    //private var currentNoteId :Int? = null


    fun onEvent(event : MusicEvent){

        when(event){
            is MusicEvent.SaveNote-> {
                viewModelScope.launch {
                    try{
                        musicUseCase.saveFavMusic(
                            Music(
                                id = musicId.value,
                                title = title.value,
                                duration = duration.value,
                                heartButton = heartButton.value,
                                picOfSong = picOfSong.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)

                    }catch(e: Exception){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save the music!"
                            )
                        )
                    }
                }
            }
            else -> Unit
        }
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote :UiEvent()
    }
}