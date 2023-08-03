package com.example.composesubmission.ui.main

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composesubmission.R
import com.example.composesubmission.data.Club
import com.example.composesubmission.di.Injection
import com.example.composesubmission.ui.factory.ViewModelFactory
import com.example.composesubmission.ui.util.UiState

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel(factory = ViewModelFactory(Injection.clubRepo())),
    navigateToDetail: (String) -> Unit,
) {
    val query by mainViewModel.query
    mainViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                mainViewModel.searchClub(query)
            }
            is UiState.Success -> {
                MainContent(
                    query = query,
                    onQueryChange = mainViewModel::searchClub,
                    clubList = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {
                Text(uiState.message)
            }
            else -> {
                Text(R.string.unexpected.toString())
            }
        }
    }
}

@Composable
fun MainContent(
    query: String,
    onQueryChange: (String) -> Unit,
    clubList: List<Club>,
    navigateToDetail: (String) -> Unit,
) {
    Column {
        SearchItem(
            query = query,
            onQueryChange = onQueryChange
        )
        ListClub(
            listClub = clubList,
            navigateToDetail = navigateToDetail
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListClub(
    listClub: List<Club>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("lazy_list")
    ) {
        items(listClub, key = { it.name }) { item ->
            ClubItem(
                name = item.name,
                photo = item.photo,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.name) }
            )
        }
    }
}
