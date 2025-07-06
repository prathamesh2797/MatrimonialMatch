package com.example.matrimonialmatch.domain.repository

import com.example.matrimonialmatch.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<List<UserProfile>>

    suspend fun syncUsersFromApi(age: Int, city: String)

    suspend fun fetchUsersByPage(page: Int): List<UserProfile>

    suspend fun insertUsers(users: List<UserProfile>)

    suspend fun acceptUser(user: UserProfile)

    suspend fun declineUser(user: UserProfile)
}