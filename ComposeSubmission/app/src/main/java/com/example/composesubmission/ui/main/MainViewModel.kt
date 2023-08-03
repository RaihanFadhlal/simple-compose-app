package com.example.composesubmission.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesubmission.data.Club
import com.example.composesubmission.data.ClubRepository
import com.example.composesubmission.ui.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(private val clubRepository: ClubRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Club>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Club>>>
    get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun searchClub(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        clubRepository.searchClub(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }
}