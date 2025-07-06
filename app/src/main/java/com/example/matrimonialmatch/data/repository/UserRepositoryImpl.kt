package com.example.matrimonialmatch.data.repository

import android.util.Log
import com.example.matrimonialmatch.data.local.dao.UserDao
import com.example.matrimonialmatch.data.remote.api.UserApiService
import com.example.matrimonialmatch.data.remote.dto.UserProfileResponseDTO
import com.example.matrimonialmatch.domain.model.MatchStatus
import com.example.matrimonialmatch.domain.model.UserProfile
import com.example.matrimonialmatch.domain.repository.UserRepository
import com.example.matrimonialmatch.domain.usecase.CalculateMatchScoreUseCase
import com.example.matrimonialmatch.utils.toDomain
import com.example.matrimonialmatch.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApiService,
    private val userDao: UserDao,
    private val calculateMatchScoreUseCase: CalculateMatchScoreUseCase
) : UserRepository {

    override fun getUsers(): Flow<List<UserProfile>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toDomain() }
        }
    }


    override suspend fun syncUsersFromApi(age: Int, city: String) {
        try {
            val response = api.getUsers(page = 1)

            val scoredProfiles = response.results.map {
                val domainProfile = it.toDomain().copy(
                    matchScore = calculateMatchScoreUseCase(
                        userAge = age, // hardcoded or pass dynamically later
                        userCity = city,
                        profileAge = it.dob.age,
                        profileCity = it.location.city
                    )
                )
                domainProfile.toEntity()
            }

            userDao.insertAll(scoredProfiles)

        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to fetch from API", e)
        }
    }

    override suspend fun fetchUsersByPage(page: Int): List<UserProfile> {
        return try {
            val response = api.getUsers(page = page)
            val entities = response.results.map { it.toDomain().toEntity() }
            userDao.insertAll(entities)
            entities.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun insertUsers(users: List<UserProfile>) {
        val entities = users.map { it.toEntity() }
        userDao.insertAll(entities)
    }

    override suspend fun acceptUser(user: UserProfile) {
        userDao.updateUserStatus(user.id, MatchStatus.ACCEPTED.name)
    }

    override suspend fun declineUser(user: UserProfile) {
        userDao.updateUserStatus(user.id, MatchStatus.DECLINED.name)
    }

}