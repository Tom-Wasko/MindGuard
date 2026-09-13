package com.mindguard.app

import android.app.Application
import androidx.room.Room
import com.mindguard.core.data.MindGuardDatabase
import com.mindguard.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MindGuardApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MindGuardApplication)
            modules(appModule)
        }
    }
}
