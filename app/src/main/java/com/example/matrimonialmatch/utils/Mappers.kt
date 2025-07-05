package com.example.matrimonialmatch.utils

import com.example.matrimonialmatch.data.remote.dto.UserProfileResponseDTO
import com.example.matrimonialmatch.domain.model.UserProfile

 fun UserProfileResponseDTO.Result.toDomain(): UserProfile {
        return UserProfile(
            id = login.uuid,
            name = "${name.first} ${name.last}",
            age = dob.age,
            city = location.city,
            state = location.state,
            country = location.country,
            imageUrl = picture.large,
            matchScore = 0
        )
    }
