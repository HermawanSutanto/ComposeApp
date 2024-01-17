package com.dicoding.listcompose.ui.Navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object DetailObject : Screen("detailobject/{id}") {
        fun createRoute(id: Long) = "detailobject/$id"
    }
}