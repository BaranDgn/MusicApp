package com.example.musicappws.presenter.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.musicappws.presenter.NavGraph.DetailScreens
import com.example.musicappws.Resource
import com.example.musicappws.data.model.ArtistDetail
import com.example.musicappws.data.model.ArtistDetailItem
import com.example.musicappws.presenter.viewModel.ArtistDetailViewModel
import java.net.URLEncoder

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ArtistDetailScreen(
    navController: NavHostController,
    id:Int,
    name: String,
    artist_picture: String,
    artViewModel: ArtistDetailViewModel = hiltViewModel()
) {


    val artistItem = produceState<Resource<ArtistDetail>>(initialValue = Resource.Loading()){
        value = artViewModel.loadSongOfArtist(id)
    }.value

    val scrollstate = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollstate)
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
                .fillMaxSize().height(900.dp)
        ) {

            Image(
                painter = rememberImagePainter(data = artist_picture),
                contentDescription = "Artist Cover Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                .clip(RoundedCornerShape(6.dp))
            )
            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp))
            when(artistItem){
                is Resource.Success->{
                    val selectedAlbum = artistItem.data
                    if (selectedAlbum != null) {
                        ArtListView(navController = navController, selectedAlbum)
                    }
                }
                is Resource.Error->{
                    Text(text = artistItem.message!!,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))

                }
                is Resource.Loading->{
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.align(Alignment.CenterHorizontally))
                }

            }

        }

    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ArtistList(
    navController: NavHostController,
    artistDetail: ArtistDetailItem
) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                .clickable { navController.navigate(DetailScreens.AlbumScreen.route+"/${artistDetail.id}/${artistDetail.title}/${URLEncoder.encode(artistDetail.cover_medium, java.nio.charset.StandardCharsets.UTF_8.toString())}")}

        ) {
            Image(
                painter = rememberImagePainter(data = artistDetail.cover_medium),
                contentDescription = "Album Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(110.dp)
                    .clip(RoundedCornerShape(12.dp)),


            )

            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = artistDetail.title,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = artistDetail.release_date,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
            }
        }
}

@Composable
fun ArtListView(
    navController: NavHostController,
    artDetail : ArtistDetail
    ) {

    LazyColumn(contentPadding = PaddingValues(8.dp)){
        items(artDetail.data){item ->
            ArtistList(navController = navController, artistDetail = item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



