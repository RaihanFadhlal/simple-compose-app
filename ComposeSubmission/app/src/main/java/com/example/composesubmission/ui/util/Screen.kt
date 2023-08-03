package com.example.composesubmission.ui.util

open class Screen(val route: String) {
    object Main : Screen("main")
    object Detail : Screen("main/{name}") {
        fun createRoute(name: String) = "main/$name"
    }
    object About : Screen("about")
}