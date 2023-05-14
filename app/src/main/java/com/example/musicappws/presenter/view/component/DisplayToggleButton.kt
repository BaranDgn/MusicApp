package com.example.musicappws.presenter.view

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DisplayToggleButton(
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    val checkedState = remember { mutableStateOf(false) }

    Box(

        Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {

        IconToggleButton(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                if(checkedState.value){
                    onSave()
                   // checkedState.value=true
                }else{
                    onDelete()
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {

            val transition = updateTransition(checkedState.value, label = "")

            val tint by transition.animateColor(label = "iconColor") { isChecked ->
                if (isChecked) Color.DarkGray else Color.Black
            }

            val size by transition.animateDp(
                transitionSpec = {

                    if (false isTransitioningTo true) {

                        keyframes {
                            durationMillis = 250
                            30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                            35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                            40.dp at 75 // ms
                            35.dp at 150 // ms
                        }
                    } else {
                        spring(stiffness = Spring.StiffnessVeryLow)
                    }
                },
                label = "Size"
            ) { 30.dp }

            Icon(
                imageVector = if (checkedState.value){
                    Icons.Filled.Favorite
                }
                else {Icons.Filled.FavoriteBorder},
                contentDescription = "Icon",
                tint = tint,
                modifier = Modifier.size(size)
            )
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DisplayToggleButtonForFav(
    onDelete: () -> Unit
) {
    val checkedState = remember { mutableStateOf(true) }

    Box(

        Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {

        IconToggleButton(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                if(checkedState.value){
                  //  checkedState.value = true
                }else{
                    onDelete()
                    checkedState.value=true
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {

            val transition = updateTransition(checkedState.value, label = "")

            val tint by transition.animateColor(label = "iconColor") { isChecked ->
                if (isChecked) Color.DarkGray else Color.Black
            }


            val size by transition.animateDp(
                transitionSpec = {

                    if (false isTransitioningTo true) {

                        keyframes {
                            durationMillis = 250
                            30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                            35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                            40.dp at 75 // ms
                            35.dp at 150 // ms
                        }
                    } else {
                        spring(stiffness = Spring.StiffnessVeryLow)
                    }
                },
                label = "Size"
            ) { 30.dp }

            Icon(
                imageVector = if (checkedState.value){
                    Icons.Filled.Favorite
                }
                else {Icons.Filled.FavoriteBorder},
                contentDescription = "Icon",
                tint = tint,
                modifier = Modifier.size(size)
            )
        }
    }
}