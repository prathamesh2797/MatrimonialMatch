package com.example.matrimonialmatch.utils

import com.example.matrimonialmatch.data.local.db.entity.UserEntity
import com.example.matrimonialmatch.data.remote.dto.UserProfileResponseDTO
import com.example.matrimonialmatch.domain.model.MatchStatus
import com.example.matrimonialmatch.domain.model.UserProfile



fun generateFakeReligion(): String {
    return listOf("Hindu", "Muslim", "Christian", "Jain", "Sikh", "Buddhist").random()
}

fun generateFakeEducation(): String {
    return listOf("B.Tech", "M.Tech", "MBA", "MBBS", "B.Sc", "MCA", "PhD").random()
}


fun UserProfileResponseDTO.Result.toDomain(): UserProfile {
    return UserProfile(
        id = login.uuid,
        name = "${name.first} ${name.last}",
        age = dob.age,
        city = location.city,
        state = location.state,
        country = location.country,
        imageUrl = picture.large,
        matchScore = 0,
        education = generateFakeEducation(),
        religion = generateFakeReligion()
    )
}


fun UserEntity.toDomain(): UserProfile {
    return UserProfile(
        id = id,
        name = name,
        age = age,
        city = city,
        state = state,
        country = country,
        imageUrl = imageUrl,
        matchScore = this.matchScore,
        status = status ?: MatchStatus.NONE,
        education = education,
        religion = religion
    )
}

fun UserProfile.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        age = age,
        city = city,
        state = state,
        country = country,
        imageUrl = imageUrl,
        status = status,
        matchScore = this.matchScore,
        education = education,
        religion = religion
    )
}
