package com.example.composesubmission.ui.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composesubmission.R
import com.example.composesubmission.di.Injection
import com.example.composesubmission.ui.factory.ViewModelFactory
import com.example.composesubmission.ui.util.UiState

@Composable
fun DetailScreen(
    clubName: String,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.clubRepo()))
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getClub(clubName)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailInformation(
                    name = data.name,
                    photo = data.photo,
                    description = data.description,
                    stadium = data.stadium,
                    coach = data.coach,
                    year = data.year,
                    navigateBack = navigateBack,
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
fun DetailInformation(
    name: String,
    @DrawableRes photo: Int,
    description: String,
    stadium: String,
    coach: String,
    year: Int,
    navigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(50.dp)
                .testTag("back")
                .background(Color.White)
        ) {
            Icon(
                contentDescription = stringResource(R.string.back_button),
                imageVector = Icons.Default.ArrowBack,
                tint = Color.Red
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(photo),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scrollToBottom")
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = name,
                textAlign = TextAlign.Center,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = stringResource(R.string.stadium_field),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 48.dp)
                    )
                    Text(
                        text = stadium,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = stringResource(R.string.year_field),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                    )
                    Text(
                        text = year.toString(),
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = stringResource(R.string.coach_field),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 60.dp)
                    )
                    Text(
                        text = coach,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = stringResource(R.string.desc_field),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 24.dp)
                    )
                    Text(
                        text = description,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
            }
        }
    }
}
