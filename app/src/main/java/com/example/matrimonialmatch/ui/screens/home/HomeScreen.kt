package com.example.matrimonialmatch.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.matrimonialmatch.domain.model.UserProfile
import com.example.matrimonialmatch.ui.state.UiState

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val profiles = (state as UiState.Success).profiles

            if (profiles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No matches found!")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = profiles, key = { it.id }) { profile ->
                        MatchCard(
                            profile = profile,
                            onAccept = { viewModel.acceptUser(it) },
                            onDecline = { viewModel.declineUser(it) }
                        )
                    }
                }
            }
        }
        is UiState.Error -> {
            Text("Something went wrong...")
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyProfiles = listOf(
        UserProfile(
            id = "1",
            name = "Adilson Pultrum",
            age = 56,
            city = "Oudega gem",
            state = "Smallingerlnd",
            country = "Drenthe",
            imageUrl = "",
            matchScore = 85
        ),
        UserProfile(
            id = "2",
            name = "Florence Gagné",
            age = 29,
            city = "Paris",
            state = "Île-de-France",
            country = "France",
            imageUrl = "",
            matchScore = 90
        )
    )

    HomeScreen(
        profiles = dummyProfiles,
        onAccept = {},
        onDecline = {}
    )
}*/
