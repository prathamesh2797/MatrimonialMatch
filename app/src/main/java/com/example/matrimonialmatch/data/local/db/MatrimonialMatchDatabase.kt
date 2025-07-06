package com.example.matrimonialmatch.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.matrimonialmatch.data.local.converters.Converters
import com.example.matrimonialmatch.data.local.dao.UserDao
import com.example.matrimonialmatch.data.local.db.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MatchMateDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}