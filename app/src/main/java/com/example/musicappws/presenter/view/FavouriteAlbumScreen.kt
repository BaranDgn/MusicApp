package com.example.musicappws.presenter.view


import android.text.format.DateUtils

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.musicappws.data.model.Music
import com.example.musicappws.presenter.favMusic.FavMusicViewModel
import com.example.musicappws.presenter.favMusic.MusicEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FavouriteAlbumScreen(
    navHostController: NavHostController,
    favMusicViewModel: FavMusicViewModel = hiltViewModel()
) {

    val state = favMusicViewModel.state.value

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(900.dp)
            .background(Color.White)
            .padding(4.dp, 8.dp, 4.dp, 64.dp)
            ,
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Favourite Songs",
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
                .padding(4.dp)
        ) {

            LazyColumn(contentPadding = PaddingValues(8.dp)){
                items(state.musics){music->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            //.border(1.dp, Color.Black)
                            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                            .clickable {

                            }
                    ) {
                        Image(
                            painter = rememberImagePainter(data = music.picOfSong),
                            contentDescription = "Song's Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(120.dp)
                                .height(110.dp)
                             .clip(RoundedCornerShape(12.dp)),
                        )

                        Row(modifier = Modifier,
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.width(180.dp),
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(4.dp),
                                    text = music.title,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Start,
                                    color = Color.Black
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(4.dp),
                                    text = formatDuration(music.duration.toFloat()).toString(),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Start,
                                    color = Color.Black
                                )

                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(2.dp)
                            ){

                                DisplayToggleButtonForFav(
                                    onDelete = {
                                        favMusicViewModel.onEvent(MusicEvent.deleteMusic(music))
                                        scope.launch {
                                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Music deleted",
                                                actionLabel = "Undo"
                                            )
                                            //we clicked on the snackbar
                                            if(result == SnackbarResult.ActionPerformed){
                                                favMusicViewModel.onEvent(MusicEvent.RestoreMusic)

                                            }
                                        }}
                                )
                            }


                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }


            //FavMusicList(state.musics)

        }

    }

}

fun formatFavDuration(seconds: Float): String = if (seconds < 60) {
    seconds.toString()
} else {
    DateUtils.formatElapsedTime(seconds.toLong())
}