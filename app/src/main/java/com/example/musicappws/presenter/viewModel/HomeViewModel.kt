package com.example.musicappws.presenter.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicappws.Resource
import com.example.musicappws.data.model.CategoryItem
import com.example.musicappws.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: MusicRepository
) :ViewModel(){

    var listOfCategory = mutableStateOf<List<CategoryItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init{
        gettingCategory()
    }

    fun gettingCategory(){
        viewModelScope.launch {
            isLoading.value = true

            when(val result = repo.loadCategories()){
                is Resource.Success->{
                    val categoryItem = result.data!!.data.mapIndexed { index, categoryItem ->
                        CategoryItem(
                            categoryItem.id,
                            categoryItem.name,
                            categoryItem.picture_medium
                        )
                    }
                    errorMessage.value = ""
                    isLoading.value = false
                    listOfCategory.value += categoryItem

                }
                is Resource.Error->{
                    errorMessage.value = "repo.loadCategories().message!!.toString()"
                    isLoading.value = false
                }
                else -> Unit
            }
        }

    }


}