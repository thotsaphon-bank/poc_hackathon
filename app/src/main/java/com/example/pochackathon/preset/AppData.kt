package com.example.pochackathon.preset

import com.example.pochackathon.model.User

object AppData {
    var users = listOf(
        User(1.toString(), true),
        User(2.toString(), false),
        User(3.toString(), false),
        User(4.toString(), false),
        User(5.toString(), false)
    )
}