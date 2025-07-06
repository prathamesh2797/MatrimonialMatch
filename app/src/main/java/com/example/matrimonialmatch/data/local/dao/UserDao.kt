package com.example.matrimonialmatch.data.local.dao


import androidx.room.*
import com.example.matrimonialmatch.data.local.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_profiles")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("UPDATE user_profiles SET status = :status WHERE id = :userId")
    suspend fun updateUserStatus(userId: String, status: String)
}