package com.example.musicappws.presenter.bottomBarNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreens(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Home : BottomBarScreens(
        route = "HOME",
        title = "HOME",
        icon = Icons.Filled.Home
    )
    object Music : BottomBarScreens(
        route = "MUSIC",
        title = "MUSIC",
        icon = Icons.Filled.Favorite
    )
}
