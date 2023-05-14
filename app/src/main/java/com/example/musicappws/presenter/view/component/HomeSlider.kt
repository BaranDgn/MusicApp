package com.example.musicappws.presenter.view.component

import androidx.annotation.DrawableRes
import com.example.musicappws.R

sealed class HomeSlider(
    @DrawableRes
    val image: Int,
    val content: String,

    ){
    object FirstPage: HomeSlider(
        image = R.drawable.musicsix,
        content = "Listen What you like",
    )
    object SecondPage: HomeSlider(
        image = R.drawable.musicfive,
        content = "can choose your favourite artist",
    )
    object ThirdPage: HomeSlider(
        image = R.drawable.musicone,
        content = "liked it, click the liked button",
    )

}