package com.example.musicappws.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.example.musicappws.Resource
import com.example.musicappws.data.model.Artist
import com.example.musicappws.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repo : MusicRepository
) : ViewModel(){

    /*
    var listOfCat = mutableStateOf<List<ArtistItem>>(listOf())
    var error = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    //suspend fun getArtistByCategory(id: Int): Resource<Artist>{
    //    return repo.loadArtist(id)
   // }
    //var id : Int?=null
    init{
       // id?.let { getArtistByCategory(it) }

        //getArtistByCategory(id!!)
    }*/

    suspend fun getArtistByCategoryTwo(id: Int): Resource<Artist>{
            return repo.loadArtist(id)
    }

/*
    fun getArtistByCategory(genreId: Int) {
        viewModelScope.launch {
            isLoading.value = true

            val result = repo.loadArtist(genreId)
            when(result){
                is Resource.Success ->{
                    val artistItem = result.data!!.data.mapIndexed { index, artistItem ->
                        ArtistItem(
                            artistItem.id,
                            artistItem.name,
                            artistItem.picture,
                            artistItem.picture_big,
                            artistItem.picture_medium,
                            artistItem.picture_small,
                            artistItem.picture_xl,
                            artistItem.radio,
                            artistItem.tracklist,
                            artistItem.type,
                        )
                    }
                    isLoading.value = false
                    error.value = ""
                    listOfCat.value += artistItem

                }
                is Resource.Error ->{
                    error.value = ""
                    isLoading.value = false
                }
                else -> Unit
            }
        }
    }*/
}