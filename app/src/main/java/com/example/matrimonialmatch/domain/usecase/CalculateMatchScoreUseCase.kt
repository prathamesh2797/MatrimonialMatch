package com.example.matrimonialmatch.domain.usecase

import com.example.matrimonialmatch.domain.model.UserProfile
import javax.inject.Inject

class CalculateMatchScoreUseCase @Inject constructor() {
    operator fun invoke(profile: UserProfile): Int {
        return (30..99).random()
    }
}