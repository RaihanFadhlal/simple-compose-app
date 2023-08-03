package com.example.composesubmission.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesubmission.data.Club
import com.example.composesubmission.data.ClubRepository
import com.example.composesubmission.ui.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val clubRepository: ClubRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Club>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Club>>
    get() = _uiState

    fun getClub(name: String) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(clubRepository.getClub(name))
    }


}