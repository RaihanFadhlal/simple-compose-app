package com.example.composesubmission.data

import kotlinx.coroutines.flow.flow

class ClubRepository {
    private val clubList = mutableListOf<Club>()

    init {
        if (clubList.isEmpty()) {
            ClubInit.clubList.forEach {
                clubList.add(it)
            }
        }
    }

    fun searchClub(query: String) = flow {
        val data = clubList.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun getClub(name: String): Club {
        return clubList.first {
            it.name == name
        }
    }

    companion object {
        @Volatile
        private var instance: ClubRepository? = null

        fun getInstance(): ClubRepository =
            instance ?: synchronized(this) {
                ClubRepository().apply {
                    instance = this
                }
            }
    }
}