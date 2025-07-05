package com.example.matrimonialmatch.ui.state

import com.example.matrimonialmatch.domain.model.UserProfile

sealed class UiState {
    object Loading : UiState()
    data class Success(val profiles: List<UserProfile>) : UiState()
    data class Error(val message: String) : UiState()
}