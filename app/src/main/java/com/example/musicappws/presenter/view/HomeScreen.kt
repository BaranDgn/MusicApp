package com.example.musicappws.presenter.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.musicappws.data.model.CategoryItem
import com.example.musicappws.presenter.NavGraph.DetailScreens
import com.example.musicappws.presenter.view.component.HomeSlider
import com.example.musicappws.presenter.viewModel.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
navController: NavHostController = rememberNavController()
) {

    val sliderPage = listOf(
        HomeSlider.FirstPage,
        HomeSlider.SecondPage,
        HomeSlider.ThirdPage
    )


    val pagerState = rememberPagerState()
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit){
        while (true){
            yield()
            delay(20000)
            tween<Float>(600)
            tween<Float>(600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)  //fillMaxSize()
        .background(Color.White)
        .padding(0.dp, 0.dp, 0.dp, 64.dp)

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)

            //.padding(16.dp)

        ) {

            HorizontalPager(
                state = pagerState,
                count = 3,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                //.padding(0.dp, 20.dp, 0.dp, 20.dp)//.height(380.dp)
            ) { page ->

                SliderPage(homeSlider = sliderPage[page])
                HorizontalPagerIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(0.dp, 0.dp, 0.dp, 24.dp),
                    pagerState = pagerState
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                contentAlignment = Alignment.TopCenter

            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "MUSIC APP",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    textAlign = TextAlign.Center,
                    color = Color(0xffFBFCF8),
                    fontWeight = FontWeight.Normal
                )
            }

        }

        //HomeScreen- Categories

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(800.dp)) {
            Text(
                text = "Categories",
                fontSize = 24.sp,
                color= Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp, 4.dp, 4.dp, 0.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))

            CategoryList(navController)

        }


    }
}

@Composable
fun SliderPage(homeSlider: HomeSlider) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(380.dp)
            .padding(0.dp, 0.dp, 0.dp, 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(id = homeSlider.image),
            contentDescription = "Pager Image",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = homeSlider.content,
            fontSize = MaterialTheme.typography.h6.fontSize,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            color = Color(0xffFBFCF8)
        )

    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CategoriesGridList(
    navController: NavController,
    catItem: List<CategoryItem>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content ={
           items(catItem){category->
               CardView(
                   painter = rememberImagePainter(data = category.picture_medium),
                   contentDescription = category.name,
                   title = category.name,
                   onClick = { navController.navigate(DetailScreens.ArtistScreen.route+"/${category.id}/${category.name.replace("/", "", true)}")}
               )
           }
        }
    )
}


@Composable
fun CategoryList(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val category by remember{
        viewModel.listOfCategory
    }
    val isLoading by remember{
        viewModel.isLoading
    }
    val errorMessage by remember{
        viewModel.errorMessage
    }

    CategoriesGridList(navController = navController, catItem = category)
    Box(contentAlignment = Alignment.Center
    ){
        if(isLoading){
            CircularProgressIndicator(color = Color.Black, modifier = Modifier.align(Alignment.Center))
        }
        if(errorMessage.isNotEmpty()){
            RetryView(error = errorMessage) {
                viewModel.gettingCategory()
            }
        }
    }
}


@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
) {
    Column() {
        Text(text = error, color = Color.Blue, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}