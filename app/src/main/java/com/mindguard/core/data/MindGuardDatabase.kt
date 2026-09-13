package com.mindguard.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mindguard.cognitive.data.CognitiveTaskResultEntity
import com.mindguard.mentor.data.dao.ChatMessageDao
import com.mindguard.mentor.data.entity.ChatMessageEntity
import com.mindguard.mindfulness.data.MindfulnessLogDao
import com.mindguard.mindfulness.data.MindfulnessLogEntity
import com.mindguard.onboarding.data.dao.UserProfileDao
import com.mindguard.onboarding.data.entity.UserProfileEntity

@Database(
    entities = [
        UserProfileEntity::class,
        ChatMessageEntity::class,
        MindfulnessLogEntity::class,
        CognitiveTaskResultEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(MindGuardConverters::class)
abstract class MindGuardDatabase : RoomDatabase() {
    abstract val userProfileDao: UserProfileDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val mindfulnessLogDao: MindfulnessLogDao
}
