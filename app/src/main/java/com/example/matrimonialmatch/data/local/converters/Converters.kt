package com.example.matrimonialmatch.data.local.converters

import androidx.room.TypeConverter
import com.example.matrimonialmatch.domain.model.MatchStatus

class Converters {

    @TypeConverter
    fun fromMatchStatus(value: MatchStatus?): String? {
        return value?.name
    }

    @TypeConverter
    fun toMatchStatus(value: String?): MatchStatus? {
        return value?.let { MatchStatus.valueOf(it) }
    }
}