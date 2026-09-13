package com.mindguard.panic.service

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.view.accessibility.AccessibilityEvent

/**
 * MindGuard Accessibility Service – Fortress Mode (Sideload/F-Droid only).
 *
 * WARNING: This service uses AccessibilityService to detect when the user
 * leaves MindGuard during Panic Protocol and returns them to the app.
 * Per Google Play policy, apps using Accessibility Services must serve
 * people with disabilities. This service is ONLY for sideloaded builds.
 *
 * For Google Play distribution, use PanicOverlayService (SYSTEM_ALERT_WINDOW) instead.
 */
class MindGuardAccessibilityService : AccessibilityService() {

    companion object {
        var isPanicProtocolActive = false
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (!isPanicProtocolActive) return

        val packageName = event.packageName?.toString() ?: return

        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
            AccessibilityEvent.TYPE_WINDOWS_CHANGED -> {
                // Dismiss notification shade if user pulls it down
                if (packageName == "com.android.systemui") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        performGlobalAction(GLOBAL_ACTION_DISMISS_NOTIFICATION_SHADE)
                    } else {
                        performGlobalAction(GLOBAL_ACTION_HOME)
                    }
                }
                // If user tries to go to another app, return home
                else if (packageName != "com.mindguard" &&
                    packageName != "com.android.launcher" &&
                    !packageName.startsWith("com.android.systemui")
                ) {
                    performGlobalAction(GLOBAL_ACTION_HOME)
                }
            }
        }
    }

    override fun onInterrupt() {
        // Service interrupted
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        // Service is connected and ready
    }
}
