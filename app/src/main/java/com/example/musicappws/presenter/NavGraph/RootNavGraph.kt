package com.example.musicappws.presenter.NavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicappws.presenter.bottomBarNav.BottomBar

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController ,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ){

        composable(route = Graph.HOME){
            BottomBar()
            // HomeScreen()
        }


    }
}

object Graph{
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val DETAIL = "detail_graph"
}