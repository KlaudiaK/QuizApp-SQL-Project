package com.android.quizzy.presentation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithBackButton(
    navigator: DestinationsNavigator,
    content: @Composable() () -> Unit){

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                       navigator.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },

            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent, navigationIconContentColor = Color.Black)
            )
        }, content = {
           content()
        })
}