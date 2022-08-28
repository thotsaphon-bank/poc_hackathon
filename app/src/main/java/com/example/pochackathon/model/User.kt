package com.example.pochackathon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val isSelected: Boolean
) : Parcelable
