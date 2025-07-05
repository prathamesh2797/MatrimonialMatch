package com.example.matrimonialmatch.domain.repository

import com.example.matrimonialmatch.domain.model.MatchStatus
import com.example.matrimonialmatch.domain.model.UserProfile

interface UserRepository {
    suspend fun getUsers(): List<UserProfile>
    suspend fun updateUserStatus(userId: String, status: MatchStatus)
}