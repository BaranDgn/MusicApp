package com.example.musicappws.presenter.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.musicappws.Resource
import com.example.musicappws.data.model.Artist

import com.example.musicappws.presenter.NavGraph.DetailScreens
import com.example.musicappws.presenter.viewModel.ArtistViewModel
import java.net.URLEncoder


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ArtistScreen(
    navController: NavHostController,
    id: Int,
    name: String,
    CatViewModel: ArtistViewModel = hiltViewModel()
) {



    //ArtistListView(navController = navController, artistList = artists)


     val artItem = produceState<Resource<Artist>>(initialValue = Resource.Loading()) {
       value = CatViewModel.getArtistByCategoryTwo(id)
    }.value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),

    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                fontSize = MaterialTheme.typography.h5.fontSize,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(800.dp)
                .padding(4.dp)
        ) {

            when(artItem){
                is Resource.Success ->{
                    val selectedCat = artItem.data!!
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        content ={
                            items(selectedCat.data){artist->
                                CardView(
                                    painter = rememberImagePainter(data = artist.picture_medium),
                                    contentDescription = "name",
                                    title = artist.name,
                                    onClick = {navController.navigate(DetailScreens.ArtistDetailScreen.route+"/${artist.id}/${artist.name}/${URLEncoder.encode(artist.picture_medium, java.nio.charset.StandardCharsets.UTF_8.toString())}")}
                                )

                            }
                        }
                    )
                }
                is Resource.Error ->{
                    Text(text = artItem.message!!,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))
                }
                is Resource.Loading ->{
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.align(Alignment.CenterHorizontally))
                }

            }

        }

    }
    
}




