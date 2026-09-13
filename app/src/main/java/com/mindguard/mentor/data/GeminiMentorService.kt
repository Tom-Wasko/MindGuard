package com.mindguard.mentor.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GeminiMentorService(private val apiKey: String) {

    companion object {
        private val SYSTEM_PROMPT = """
            Jesteś empatycznym mentorem wspierającym osobę w radzeniu sobie z nałogiem i trudnymi emocjami.
            
            ZASADY BEZWZGLĘDNE:
            1. NIGDY nie osądzaj. NIGDY nie moralizuj. NIGDY nie wytykaj błędów.
            2. Stosuj zasady Dialogu Motywującego (MI): Otwarte pytania, Dowartościowanie, Odzwierciedlenia, Podsumowania (OARS).
            3. Analizuj sytuację użytkownika z perspektywy neuropsychologii: Wyjaśnij, że głód nałogowy to dopaminowy sygnał mózgu, który naturalnie maleje w czasie.
            4. Jeśli użytkownik zgłosi nawrót/wpadkę: Zastosuj Dialektyczną Abstynencję:
               - Zdejmij poczucie winy: "Potknięcie się zdarzyło. Wstyd tylko wepchnie cię z powrotem."
               - Przeprowadź Analizę Łańcuchową: Co wywołało? Czynnik podatności? Brakujące ogniwo?
               - Natychmiastowy powrót na ścieżkę bez dramatyzowania.
            5. Pamiętaj profil użytkownika wstrzyknięty w kontekście.
            6. Odpowiadaj krótko (max 3-4 zdania). Zadaj jedno otwarte pytanie na koniec.
            7. Język: dostosuj do języka użytkownika (PL lub EN).
            
            DUCH PODEJŚCIA (PACE): Partnerstwo, Akceptacja, Współczucie, Wydobywanie.
        """.trimIndent()
    }

    private fun buildModel(userContext: String = ""): GenerativeModel {
        val fullSystemPrompt = if (userContext.isNotBlank()) {
            "$SYSTEM_PROMPT\n\nKONTEKST UŻYTKOWNIKA:\n$userContext"
        } else SYSTEM_PROMPT

        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey,
            generationConfig = generationConfig {
                temperature = 0.75f
                topP = 0.95f
                topK = 40
                maxOutputTokens = 600
            },
            systemInstruction = content { text(fullSystemPrompt) }
        )
    }

    fun streamResponse(prompt: String, userContext: String = ""): Flow<String> {
        return buildModel(userContext)
            .generateContentStream(prompt)
            .map { chunk -> chunk.text ?: "" }
            .catch { e -> emit("[Error: ${e.message}]") }
    }

    suspend fun getResponse(prompt: String, userContext: String = ""): String {
        return try {
            buildModel(userContext)
                .generateContent(prompt)
                .text ?: ""
        } catch (e: Exception) {
            getFallbackResponse(prompt)
        }
    }

    // Offline fallback responses for common scenarios
    private fun getFallbackResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("chce") || lower.contains("chcę") || lower.contains("want") ->
                "Rozumiem, że teraz czujesz silną potrzebę. To naturalna fala dopaminowa w Twoim mózgu – ona minie. Co dzieje się w Twoim ciele teraz?"
            lower.contains("nawrót") || lower.contains("pekl") || lower.contains("relapse") ->
                "Potknięcie się zdarza. To nie upadek moralny – to brakujące ogniwo umiejętności. Weź oddech. Czego nauczyłeś się z tej sytuacji?"
            lower.contains("nie mogę") || lower.contains("cannot") || lower.contains("can't") ->
                "Słyszę Cię. Teraz jest ciężko. Co jest jedną małą rzeczą, którą możesz teraz zrobić dla siebie?"
            lower.contains("nudzi") || lower.contains("bored") ->
                "Nuda to częsty trigger. Twój mózg szuka stymulacji. Co ciekawego możesz teraz zrobić przez 10 minut?"
            lower.contains("stress") || lower.contains("stressed") || lower.contains("stres") ->
                "Stres aktywuje te same neurochemiczne ścieżki co nałóg. Spróbuj teraz techniki STOP – zanim zareagujesz automatycznie. Co konkretnie Cię stresuje?"
            else ->
                "Jestem tu z Tobą. Jak się teraz czujesz? Co wywołało ten impuls?"
        }
    }
}
