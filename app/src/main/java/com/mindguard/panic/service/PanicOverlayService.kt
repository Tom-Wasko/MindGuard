package com.mindguard.panic.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Build
import androidx.core.app.NotificationCompat

/**
 * Panic Protocol Overlay Service (Google Play safe version).
 *
 * Uses TYPE_APPLICATION_OVERLAY (SYSTEM_ALERT_WINDOW permission) to show
 * a full-screen overlay during the 200-second Panic Protocol.
 * This approach is compatible with Google Play policies.
 *
 * The actual 200-second UI is handled within PanicScreen composable.
 * This service exists to keep the process alive as a foreground service.
 */
class PanicOverlayService : Service() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "mindguard_panic"
        const val NOTIFICATION_ID = 1001
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "MindGuard Panic Protocol",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Active during Panic Protocol mindfulness session"
                setShowBadge(false)
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification() = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        .setContentTitle("MindGuard – Panic Protocol Active")
        .setContentText("200-second mindfulness fortress is active")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setOngoing(true)
        .build()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        MindGuardAccessibilityService.isPanicProtocolActive = false
    }
}
