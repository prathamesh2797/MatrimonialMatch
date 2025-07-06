package com.example.matrimonialmatch.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.matrimonialmatch.ui.state.UiState

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    var lastScrollOffset by remember { mutableIntStateOf(0) }

    when (uiState) {
        is UiState.Success -> {
            val profiles = (uiState as UiState.Success).profiles

            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemScrollOffset }
                    .collect { currentOffset ->
                        val layoutInfo = listState.layoutInfo
                        val totalItems = layoutInfo.totalItemsCount
                        val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                        val isScrollingUp = currentOffset < lastScrollOffset
                        lastScrollOffset = currentOffset

                        val isAtBottom = lastVisibleIndex == totalItems - 1

                        if (isScrollingUp && isAtBottom) {
                            viewModel.loadMoreUsers()
                        }
                    }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Profile Matches",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(profiles, key = { it.id }) { profile ->
                        MatchCard(
                            profile = profile,
                            onAccept = { viewModel.acceptUser(it) },
                            onDecline = { viewModel.declineUser(it) }
                        )
                    }
                }
            }
        }

        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Error -> Text("Something went wrong", color = Color.Red)
    }
}


/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyProfiles = listOf(
        UserProfile(
            id = "1",
            name = "test",
            age = 56,
            city = "Mumbai",
            state = "mumbai",
            country = "mumbai",
            imageUrl = "",
            matchScore = 85
        ),
        UserProfile(
            id = "2",
            name = "test",
            age = 29,
            city = "mumbai",
            state = "mumbai",
            country = "mumbai",
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
