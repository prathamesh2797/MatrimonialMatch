package com.example.matrimonialmatch.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.matrimonialmatch.domain.model.MatchStatus

@Entity(tableName = "user_profiles")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val age: Int,
    val city: String,
    val state: String,
    val country: String,
    val imageUrl: String,
    val status: MatchStatus? = null,
    val matchScore: Int
)