package com.example.matrimonialmatch.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matrimonialmatch.domain.model.UserProfile
import com.example.matrimonialmatch.domain.repository.UserRepository
import com.example.matrimonialmatch.domain.usecase.CalculateMatchScoreUseCase
import com.example.matrimonialmatch.ui.state.UiState
import com.example.matrimonialmatch.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.update
import java.io.IOException

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
    private val calculateMatchScoreUseCase: CalculateMatchScoreUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState
    private var currentPage = 1
    private var isLoadingMore = false

    private val currentUserAge = 62
    private val currentUserCity = "Saint-Pierre"

    init {
        //fetchProfiles()
        fetchProfilesWithRetry(age = currentUserAge, city = currentUserCity)
    }

    private fun fetchProfilesWithRetry(age: Int, city: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            var success = false

            repeat(3) { attempt ->
                try {
                    repository.syncUsersFromApi(age, city)
                    success = true
                    return@repeat
                } catch (e: IOException) {
                    Log.e("HomeViewModel", "Retry ${attempt + 1} failed", e)
                }
            }

            // Whether sync failed or succeeded, still observe Room DB
            repository.getUsers().collectLatest { profiles ->
                if (profiles.isNotEmpty()) {
                    _uiState.value = UiState.Success(profiles)
                } else if (!success) {
                    _uiState.value = UiState.Error("Offline and no cached profiles available.")
                }
            }
        }
    }


  /*  private fun fetchProfiles() {
        viewModelScope.launch {
            try {
                repository.syncUsersFromApi(age = currentUserAge, city = currentUserCity) // Fetch from API and store in Room

                repository.getUsers().collectLatest { profiles ->
                    _uiState.value = UiState.Success(profiles)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load profiles")
            }
        }
    }*/


    fun loadMoreUsers() {
        if (isLoadingMore) return
        isLoadingMore = true

        viewModelScope.launch {
            val newProfiles = repository.fetchUsersByPage(++currentPage)

            val scoredProfiles = newProfiles.map {
                it.copy(
                    matchScore = calculateMatchScoreUseCase(
                        currentUserAge,
                        currentUserCity,
                        it.age,
                        it.city
                    )
                )
            }

            repository.insertUsers(scoredProfiles)
            Log.d("HomeViewModelp", "Loaded ${scoredProfiles} new profiles with scores")

            _uiState.update {
                val old = (it as? UiState.Success)?.profiles ?: emptyList()
                UiState.Success(old + scoredProfiles)
            }

            isLoadingMore = false
        }
    }

    fun acceptUser(profile: UserProfile) {
        viewModelScope.launch {
            repository.acceptUser(profile)
        }
    }

    fun declineUser(profile: UserProfile) {
        viewModelScope.launch {
            repository.declineUser(profile)
        }
    }
}