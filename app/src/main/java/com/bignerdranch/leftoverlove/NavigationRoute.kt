package com.bignerdranch.leftoverlove

sealed class NavigationRoute(val route: String) {
    object Home : NavigationRoute("home")
    object Activity : NavigationRoute("Activity")
    object Profile : NavigationRoute("profile")
}