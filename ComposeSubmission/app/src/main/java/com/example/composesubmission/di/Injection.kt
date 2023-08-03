package com.example.composesubmission.di

import com.example.composesubmission.data.ClubRepository

object Injection {
    fun clubRepo(): ClubRepository {
        return ClubRepository.getInstance()
    }
}