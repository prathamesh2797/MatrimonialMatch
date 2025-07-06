package com.example.matrimonialmatch.data.remote.api

import com.example.matrimonialmatch.data.remote.dto.UserProfileResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 10
    ): UserProfileResponseDTO
}