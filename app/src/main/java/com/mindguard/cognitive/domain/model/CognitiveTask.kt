package com.mindguard.cognitive.domain.model

data class CognitiveTask(
    val id: String,
    val type: TaskType,
    val questionPl: String,
    val questionEn: String,
    val options: List<String>? = null,  // For MULTIPLE_CHOICE: ["A) ...", "B) ...", "C) ...", "D) ..."]
    val correctAnswer: String,          // For TRUE_FALSE: "true"/"false", MC: "A"/"B"/"C"/"D", NUMERIC: number string
    val explanationPl: String = "",
    val explanationEn: String = ""
)

enum class TaskType { TRUE_FALSE, MULTIPLE_CHOICE, OPEN_NUMERIC }
