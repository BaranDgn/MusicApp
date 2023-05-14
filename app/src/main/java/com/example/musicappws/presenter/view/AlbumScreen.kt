package com.example.musicappws.presenter.view


import android.content.Context
import android.media.MediaPlayer
import android.text.format.DateUtils
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.musicappws.R
import com.example.musicappws.Resource
import com.example.musicappws.data.model.AlbumDetail
import com.example.musicappws.data.model.AlbumDetailItem
import com.example.musicappws.presenter.viewModel.AlbumViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.musicappws.presenter.favMusic.MusicEvent



@Composable
fun AlbumScreen(
    navController: NavHostController,
    albumViewModel: AlbumViewModel= hiltViewModel(),
    albumId: Int,
    albumName : String,
    coverPic: String
) {
    var isVisible by remember{
        mutableStateOf(false)
    }

    var index by remember {
        mutableStateOf(0)
    }


    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    var songId = remember{albumViewModel.musicId.value}
    var title = remember{albumViewModel.title.value}
    var duration = remember{albumViewModel.duration.value}
    var picOfSong = remember{albumViewModel.picOfSong.value}
    var heartButton = remember{albumViewModel.heartButton.value}

   // val state = albumViewModel.state.value


    val songItem = produceState<Resource<AlbumDetail>>(initialValue = Resource.Loading()){
        value = albumViewModel.loadOfSongOfAlbum(albumId)
    }.value

    val scrollstate = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = albumName,
                        modifier = Modifier.fillMaxWidth().padding(0.dp,0.dp,8.dp,0.dp),
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 10.dp
            )

        }

    ){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(900.dp)
            .verticalScroll(scrollstate)
            .background(Color.White)
            .padding(4.dp)
            .padding(0.dp, 64.dp, 0.dp, 0.dp),
        ) {



        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(900.dp)
                .padding(4.dp)
        ) {
            when(songItem){
                is Resource.Success->{
                    val selectedAlbum = songItem.data
                   //selectedAlbum = songItem.data!!
                    if (selectedAlbum != null) {

                        LazyColumn(contentPadding = PaddingValues(5.dp)){
                            items(selectedAlbum.data){item->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                        //.border(1.dp, Color.Black)
                                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                                        .clickable {
                                            isVisible = !isVisible
                                            index = selectedAlbum.data.indexOf(item)

                                        }
                                ) {
                                    Image(
                                        painter = rememberImagePainter(data = coverPic),
                                        contentDescription = "Song's Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .width(120.dp)
                                            .height(110.dp)
                                        // .clip(RoundedCornerShape(12.dp)),
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
                                                text = item.title,
                                                fontSize = 18.sp,
                                                textAlign = TextAlign.Start,
                                                color = Color.Black
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .padding(4.dp),
                                                text = formatDuration(item.duration.toFloat()),
                                                fontSize = 14.sp,
                                                textAlign = TextAlign.Start,
                                                color = Color.Black
                                            )

                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.padding(2.dp)
                                        ){


                                            DisplayToggleButton(
                                                onSave = {
                                                    songId = item.id //selectedAlbum.data[index].id
                                                    title =  item.title//selectedAlbum.data[index].title
                                                    duration = item.duration //selectedAlbum.data[index].duration
                                                    picOfSong= coverPic
                                                    heartButton = true
                                                    albumViewModel.updateData(songId,title, duration,picOfSong, heartButton)

                                                    albumViewModel.onEvent(MusicEvent.SaveNote)
                                                    }
                                            ) {
                                               // albumViewModel.onEvent(MusicEvent.deleteMusic(item.title)

                                            }
                                        }


                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }


                    //SongOfAlbumList(selectedAlbum, coverPic, isVisible)
                    }
                }
                is Resource.Error->{
                    Text(text = songItem.message!!,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally))

                }
                is Resource.Loading->{
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.align(Alignment.CenterHorizontally))
                }

            }

        }
    }

        Spacer(modifier = Modifier.height(900.dp))
        Column(//contentAlignment = Alignment.BottomCenter,
            verticalArrangement = Arrangement.Bottom,

            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ){
            if(isVisible){
                PlaySampleAudio(albumItem = songItem.data!!.data, index = index, isVisible = isVisible)
            }
        }

        it
    }

}


fun formatDuration(seconds: Float): String = if (seconds < 60) {
    seconds.toString()
} else {
    DateUtils.formatElapsedTime(seconds.toLong())
}




@Composable
fun PlaySampleAudio(
    context: Context = LocalContext.current,
    albumItem: List<AlbumDetailItem>,
    index:Int,
    isVisible: Boolean
) {
    
    val sampleSong: MediaPlayer by remember {
        mutableStateOf(
            MediaPlayer.create(
                context,
                 albumItem[index].preview.toUri()
            )
        )
    }
    var Visibility by remember{
        mutableStateOf(isVisible)
    }
    
    // track the playing state
    var isPlaying by remember {
        mutableStateOf(false)
    }

    Card( 

        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            // Align items end to end
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (isPlaying) {
                        isPlaying = false
                        //sampleSong.stop()
                        sampleSong.reset()
                        sampleSong.release()
                        Visibility = false
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "cancel icon"
                    )
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(250.dp)
                        .padding(all = 8.dp)
                ) {
                    Text(text = albumItem[index].title)
                    Text(
                        text = albumItem[index].artist.name,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // Add control buttons
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var isPlaying by remember {
                    mutableStateOf(false)
                }
               
                // check state and set/update the icon
                IconButton(onClick = {
                    if (isPlaying) {
                        isPlaying = false
                        sampleSong.pause()
                    } else {
                        isPlaying = true
                        sampleSong.start()
                    }
                }) {
                    Icon(
                        painter = painterResource(
                            id =
                            if (isPlaying) R.drawable.pause else R.drawable.play
                        ),
                        contentDescription = "play/pause button"
                    )
                }
            }
        }
    }
}


