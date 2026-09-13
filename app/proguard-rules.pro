# ProGuard Rules for MindGuard

# Keep Room entities
-keep class com.mindguard.**.entity.** { *; }
-keep class com.mindguard.**.dao.** { *; }

# Keep Koin
-keep class org.koin.** { *; }
-keepclassmembers class * {
    @org.koin.core.annotation.* *;
}

# Keep Gemini AI SDK
-keep class com.google.ai.client.generativeai.** { *; }
-keep class com.google.protobuf.** { *; }

# Keep domain models
-keep class com.mindguard.**.domain.model.** { *; }

# Keep Compose
-keep class androidx.compose.** { *; }

# General
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
