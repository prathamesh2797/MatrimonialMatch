package com.example.matrimonialmatch.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matrimonialmatch.domain.model.MatchStatus
import com.example.matrimonialmatch.domain.model.UserProfile
import com.example.matrimonialmatch.domain.repository.UserRepository
import com.example.matrimonialmatch.domain.usecase.CalculateMatchScoreUseCase
import com.example.matrimonialmatch.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
    private val calculateScore: CalculateMatchScoreUseCase

) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            try {
                val users = repository.getUsers().map {
                    it.copy(matchScore = calculateScore(it)) // if you add matchScore
                }
                _uiState.value = UiState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load users")
            }
        }
    }

    fun acceptUser(user: UserProfile) {
        viewModelScope.launch {
            repository.updateUserStatus(user.id, MatchStatus.ACCEPTED)
        }
    }

    fun declineUser(user: UserProfile) {
        viewModelScope.launch {
            repository.updateUserStatus(user.id, MatchStatus.DECLINED)
        }
    }
}