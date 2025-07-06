package com.example.matrimonialmatch.domain.model

data class UserProfile(
    val id: String,
    val name: String,
    val age: Int,
    val city: String,
    val state: String,
    val country: String,
    val imageUrl: String,
    val matchScore: Int = 0,
    val status: MatchStatus = MatchStatus.NONE,
    val education: String,
    val religion: String
)