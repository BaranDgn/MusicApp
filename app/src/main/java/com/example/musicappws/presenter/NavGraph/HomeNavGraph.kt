package com.example.musicappws.presenter.NavGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicappws.presenter.bottomBarNav.BottomBarScreens
import com.example.musicappws.presenter.view.*

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController ,
        route = Graph.HOME,
        startDestination = BottomBarScreens.Home.route
    ){

        composable(route = BottomBarScreens.Home.route){
            HomeScreen(navController)
        }
        composable(route = BottomBarScreens.Music.route){
            FavouriteAlbumScreen(navController)
        }
        detailNavGraph(navController)
    }
}

fun NavGraphBuilder.detailNavGraph(
    navController: NavHostController
){
    navigation(
        route= Graph.DETAIL,
        startDestination = DetailScreens.ArtistScreen.route
    ){
        //DetailScreens.CategoryScreen.route+"/{id}/{name}"
        composable(
            route = "${DetailScreens.ArtistScreen.route}/{id}/{name}",
            arguments = listOf(
            navArgument("id"){
                type = NavType.IntType
            },
            navArgument("name"){
                type = NavType.StringType
            }
        )){
            val id = remember{
                it.arguments?.getInt("id")
            }
            val name = remember{
                it.arguments?.getString("name")
            }
            if(id != null){
                ArtistScreen(
                    navController = navController,
                    id = id,
                    name = name ?: ""
                )
            }
        }
        composable(
            route = "${DetailScreens.ArtistDetailScreen.route}/{id}/{name}/{artist_picture}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                },
                navArgument("name"){
                    type = NavType.StringType
                },
                navArgument("artist_picture"){
                    type = NavType.StringType
                }
            )
        ){
            val id = remember{
                it.arguments?.getInt("id")
            }
            val name = remember{
                it.arguments?.getString("name")
            }
            val artist_picture = remember{
                it.arguments?.getString("artist_picture")
            }
            if(id != null){
                ArtistDetailScreen(
                    navController = navController,
                    id = id,
                    name = name ?: "",
                    artist_picture = artist_picture ?: ""
                )
            }
        }

        composable(
            route = "${DetailScreens.AlbumScreen.route}/{id}/{name}/{cover_pic}",
            arguments = listOf(
                navArgument("id"){
                     type = NavType.IntType
                },
                navArgument("name"){
                     type = NavType.StringType
                },
                navArgument("cover_pic"){
                    type = NavType.StringType
                }

            )
        ){
            val id = remember{
                it.arguments?.getInt("id")
            }
            val name = remember{
                it.arguments?.getString("name")
            }
            val cover_pic = remember{
                it.arguments?.getString("cover_pic")
            }
            if(id != null){
            AlbumScreen(
                navController = navController,
                albumId = id,
                albumName = name ?: "",
                coverPic = cover_pic ?: ""
            )
            }
        }
    }

}

sealed class DetailScreens(val route: String){
    object ArtistScreen: DetailScreens(route = "artist_screen")
    object ArtistDetailScreen: DetailScreens(route = "artist_detail_screen")
    object AlbumScreen: DetailScreens(route = "album_screen")

}