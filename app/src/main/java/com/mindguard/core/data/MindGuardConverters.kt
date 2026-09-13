package com.mindguard.core.data

import androidx.room.TypeConverter

class MindGuardConverters {
    @TypeConverter
    fun fromStringList(list: List<String>): String =
        if (list.isEmpty()) "" else list.joinToString("|||")

    @TypeConverter
    fun toStringList(data: String): List<String> =
        if (data.isBlank()) emptyList() else data.split("|||")
}
