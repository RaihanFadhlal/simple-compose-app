package com.example.composesubmission.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Club(
    val name: String,
    val description: String,
    val photo: Int,
    val stadium: String,
    val coach: String,
    val year: Int
) : Parcelable