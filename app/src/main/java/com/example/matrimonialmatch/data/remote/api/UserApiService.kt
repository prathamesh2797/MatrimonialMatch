package com.example.matrimonialmatch.data.remote.api

import com.example.matrimonialmatch.data.remote.dto.UserProfileResponseDTO
import retrofit2.http.GET

interface UserApiService {
    @GET("api/?results=10")
    suspend fun getUsers(): UserProfileResponseDTO
}