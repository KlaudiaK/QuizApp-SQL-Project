package com.android.quizzy.presentation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(var title:String, var icon: ImageVector, var screen_route:String){

    object Home : BottomNavItem("Home", Icons.Filled.Home,"favourite_books")
    object Quizzes : BottomNavItem("Quizzes", Icons.Filled.Quiz,"quizzes")
    object Profile : BottomNavItem("Profile", Icons.Filled.Person,"profile")
   // object Login : BottomNavItem("Login", R.drawable.ic_book,"login")
   // object Register : BottomNavItem("Register", R.drawable.ic_book,"register")

}