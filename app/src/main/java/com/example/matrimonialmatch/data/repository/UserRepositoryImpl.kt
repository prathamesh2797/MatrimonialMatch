package com.example.matrimonialmatch.data.repository

import android.util.Log
import com.example.matrimonialmatch.data.remote.api.UserApiService
import com.example.matrimonialmatch.data.remote.dto.UserProfileResponseDTO
import com.example.matrimonialmatch.domain.model.MatchStatus
import com.example.matrimonialmatch.domain.model.UserProfile
import com.example.matrimonialmatch.domain.repository.UserRepository
import com.example.matrimonialmatch.utils.toDomain
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApiService
    // TODO: Add DAO here for Room fallback
) : UserRepository {

    override suspend fun getUsers(): List<UserProfile> {
        return try {
            val response: UserProfileResponseDTO = api.getUsers()
            Log.d("API_RESPONSE", "Fetched ${response.results.size} users from API. Response json: ${response}")
            response.results.map { it.toDomain() }
            //response.results.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun updateUserStatus(userId: String, status: MatchStatus) {
        // TODO: Save status to Room DB
    }
}