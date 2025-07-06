package com.example.matrimonialmatch.domain.usecase

import javax.inject.Inject

class CalculateMatchScoreUseCase @Inject constructor() {
    operator fun invoke(
        userAge: Int,
        userCity: String,
        profileAge: Int,
        profileCity: String
    ): Int {
        val cityScore = if (userCity.equals(profileCity, ignoreCase = true)) 50 else 0
        val ageDifference = kotlin.math.abs(userAge - profileAge)
        val ageScore = (50 - (ageDifference * 5)).coerceAtLeast(0)

        return cityScore + ageScore
    }
}