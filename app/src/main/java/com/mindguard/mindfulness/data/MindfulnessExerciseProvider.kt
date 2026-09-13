package com.mindguard.mindfulness.data

import com.mindguard.mindfulness.domain.model.*

object MindfulnessExerciseProvider {

    val allExercises: List<MindfulnessExercise> = listOf(
        // 1. Grounding 5-4-3-2-1
        MindfulnessExercise(
            id = "grounding_54321",
            titleKey = "grounding",
            type = ExerciseType.GROUNDING,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "Find 5 things you can SEE. Look around carefully — notice colors, shapes, shadows.",
                    instructionPl = "Znajdź 5 rzeczy, które WIDZISZ. Rozejrzyj się uważnie — dostrzeż kolory, kształty, cienie.",
                    tapCount = 5,
                    inputType = StepInputType.TAP
                ),
                ExerciseStep(
                    instructionEn = "Notice 4 things you can TOUCH. Feel the texture of your clothes, the chair beneath you.",
                    instructionPl = "Zauważ 4 rzeczy, których możesz DOTKNĄĆ. Poczuj fakturę ubrania, krzesło pod tobą.",
                    tapCount = 4,
                    inputType = StepInputType.TAP
                ),
                ExerciseStep(
                    instructionEn = "Listen for 3 things you can HEAR. A distant sound, your own breathing, ambient noise.",
                    instructionPl = "Wsłuchaj się w 3 dźwięki, które SŁYSZYSZ. Odległy dźwięk, własny oddech, szum otoczenia.",
                    tapCount = 3,
                    inputType = StepInputType.TAP
                ),
                ExerciseStep(
                    instructionEn = "Notice 2 things you can SMELL. Fresh air, your drink, your own scent.",
                    instructionPl = "Zauważ 2 zapachy, które CZUJESZ. Świeże powietrze, napój, własny zapach.",
                    tapCount = 2,
                    inputType = StepInputType.TAP
                ),
                ExerciseStep(
                    instructionEn = "Notice 1 thing you can TASTE. The taste in your mouth, or take a sip of water. Now take one deep breath.",
                    instructionPl = "Zauważ 1 smak, który CZUJESZ. Smak w ustach lub łyk wody. Teraz weź jeden głęboki wdech.",
                    tapCount = 1,
                    inputType = StepInputType.TAP
                )
            )
        ),

        // 2. STOP Technique
        MindfulnessExercise(
            id = "stop_technique",
            titleKey = "stop",
            type = ExerciseType.TIMED,
            totalDurationSeconds = 60,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "S — STOP. Freeze completely for 5 seconds. Your emotions are pushing you toward automatic action. Stay still.",
                    instructionPl = "S — STOP. Całkowicie zastygaj przez 5 sekund. Twoje emocje próbują wywołać automatyczną reakcję. Pozostań w bezruchu.",
                    durationSeconds = 10
                ),
                ExerciseStep(
                    instructionEn = "T — TAKE A BREATH. Step back physically or lean into your seat. Take one slow, deep diaphragmatic breath.",
                    instructionPl = "T — WEŹ ODDECH. Zrób fizyczny krok w tył lub oprzyj się wygodnie. Weź jeden powolny, głęboki oddech przeponowy.",
                    durationSeconds = 15
                ),
                ExerciseStep(
                    instructionEn = "O — OBSERVE. What is happening in your body? What thoughts are flowing? Separate facts from interpretations.",
                    instructionPl = "O — OBSERWUJ. Co dzieje się w Twoim ciele? Jakie myśli przepływają? Oddziel fakty od interpretacji.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "P — PROCEED MINDFULLY. Ask yourself: What action right now will be effective and aligned with my values — not my impulse?",
                    instructionPl = "P — POSTĘPUJ UWAŻNIE. Zapytaj siebie: Jakie działanie będzie skuteczne i zgodne z moimi wartościami — nie z impulsem?",
                    durationSeconds = 15
                )
            )
        ),

        // 3. Box Breathing 4-4-4-4
        MindfulnessExercise(
            id = "box_breathing",
            titleKey = "box_breathing",
            type = ExerciseType.BREATHING,
            hasBreathingAnimation = true,
            breathingPattern = BreathingPattern(
                inhaleDuration = 4,
                holdDuration = 4,
                exhaleDuration = 4,
                cycles = 4,
                shape = BreathingShape.SQUARE
            ),
            totalDurationSeconds = 64, // 4 cycles × 16s
            steps = listOf(
                ExerciseStep(
                    instructionEn = "Box Breathing balances your nervous system. Follow the square: Inhale → Hold → Exhale → Hold. 4 cycles.",
                    instructionPl = "Oddech Pudełkowy równoważy Twój układ nerwowy. Podążaj za kwadratem: Wdech → Zatrzymanie → Wydech → Zatrzymanie. 4 cykle."
                )
            )
        ),

        // 4. Breathing 4-7-8
        MindfulnessExercise(
            id = "breathing_478",
            titleKey = "breathing_478",
            type = ExerciseType.BREATHING,
            hasBreathingAnimation = true,
            breathingPattern = BreathingPattern(
                inhaleDuration = 4,
                holdDuration = 7,
                exhaleDuration = 8,
                cycles = 4,
                shape = BreathingShape.CIRCLE
            ),
            totalDurationSeconds = 76, // 4 cycles × 19s
            steps = listOf(
                ExerciseStep(
                    instructionEn = "4-7-8 activates your vagal brake, slowing your heart and releasing tension. Inhale through nose → Long hold → Exhale through mouth with a soft whoosh.",
                    instructionPl = "4-7-8 aktywuje hamulec nerwu błędnego, spowalniając serce i uwalniając napięcie. Wdech przez nos → Długie zatrzymanie → Wydech przez usta z cichym świstem."
                )
            )
        ),

        // 5. Helicopter View
        MindfulnessExercise(
            id = "helicopter_view",
            titleKey = "helicopter",
            type = ExerciseType.REFLECTIVE,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "ZOOM 0 — Your body right now. Notice the painful emotion or tension.",
                    instructionPl = "ZOOM 0 — Twoje ciało teraz. Zauważ bolesną emocję lub napięcie.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "ZOOM 1 — Rise to the ceiling. See yourself sitting with your phone. You are someone fighting for their focus.",
                    instructionPl = "ZOOM 1 — Wznieś się pod sufit. Spójrz na siebie siedzącego z telefonem. Widzisz kogoś, kto walczy o swoje skupienie.",
                    durationSeconds = 25
                ),
                ExerciseStep(
                    instructionEn = "ZOOM 2 — Rise above the building and city. Hundreds of people, streets, rooftops. Your struggle is one tiny point among thousands.",
                    instructionPl = "ZOOM 2 — Wznieś się nad budynek i miasto. Setki ludzi, ulice, dachy. Twój problem to jeden z tysięcy drobnych punktów.",
                    durationSeconds = 25
                ),
                ExerciseStep(
                    instructionEn = "ZOOM 3 — Perspective of time. Will this impulse matter in 7 days? In a year? In 5 years?",
                    instructionPl = "ZOOM 3 — Perspektywa czasu. Czy ten impuls będzie miał znaczenie za 7 dni? Za rok? Za 5 lat?",
                    durationSeconds = 30
                )
            )
        ),

        // 6. Wise Mind (DBT)
        MindfulnessExercise(
            id = "wise_mind",
            titleKey = "wise_mind",
            type = ExerciseType.INTERACTIVE,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "EMOTIONAL MIND — What am I feeling and demanding right now? Write your emotional reaction.",
                    instructionPl = "UMYSŁ EMOCJONALNY — Co czuję i czego żądam teraz? Opisz swoją emocjonalną reakcję.",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "RATIONAL MIND — What do cold facts and logic say? What are the objective consequences?",
                    instructionPl = "UMYSŁ RACJONALNY — Co mówią chłodne fakty i logika? Jakie są obiektywne konsekwencje?",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "WISE MIND — The intersection of both. Validate the emotion AND choose the wise action. What does your deep wisdom say?",
                    instructionPl = "MĄDRY UMYSŁ — Część wspólna obu. Zwaliduj emocję I wybierz mądre działanie. Co mówi Twoja głęboka mądrość?",
                    inputType = StepInputType.TEXT
                )
            )
        ),

        // 7. Gratitude
        MindfulnessExercise(
            id = "gratitude",
            titleKey = "gratitude",
            type = ExerciseType.INTERACTIVE,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "A sensory micro-moment from the last 24 hours. (e.g., the warmth of morning tea, a kind smile)",
                    instructionPl = "Sensoryczny mikromoment z ostatnich 24 godzin. (np. ciepło porannej herbaty, miły uśmiech)",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "An interaction with another person. Any moment of human warmth, however small.",
                    instructionPl = "Interakcja z drugim człowiekiem. Dowolny moment ludzkiego ciepła, nawet mały.",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "Something that worked without problems today. Hold this memory for 15 seconds and feel the calm in your body.",
                    instructionPl = "Coś, co dziś zadziałało bez problemu. Przytrzymaj to wspomnienie przez 15 sekund i poczuj spokój w ciele.",
                    inputType = StepInputType.TEXT,
                    durationSeconds = 15
                )
            )
        ),

        // 8. Safe Place Visualization
        MindfulnessExercise(
            id = "safe_place",
            titleKey = "safe_place",
            type = ExerciseType.TIMED,
            totalDurationSeconds = 120,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "Close your eyes. Recall a place — real or imagined — where you feel 100% safe, untouchable, and in control.",
                    instructionPl = "Zamknij oczy. Przywołaj miejsce — rzeczywiste lub wyobrażone — w którym czujesz 100% spokoju i bezpieczeństwa.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "TEMPERATURE: What does the air feel like on your skin? Warm? Cool? Gentle breeze?",
                    instructionPl = "TEMPERATURA: Jak powietrze dotyka Twojej skóry? Ciepłe? Chłodne? Delikatny wiatr?",
                    durationSeconds = 25
                ),
                ExerciseStep(
                    instructionEn = "SOUNDS: What sounds exist in the background? Waves? Trees? Crackling fire? Silence itself?",
                    instructionPl = "DŹWIĘKI: Jakie dźwięki są w tle? Fale? Drzewa? Trzask kominka? Sama cisza?",
                    durationSeconds = 25
                ),
                ExerciseStep(
                    instructionEn = "SCENT: What scent fills the air? Connect this feeling to your anchor word. Whisper it: Peace. Sanctuary. Home.",
                    instructionPl = "ZAPACH: Jaki zapach unosi się w powietrzu? Połącz to uczucie ze słowem-kotwicą. Szepnij je: Spokój. Przystań. Dom.",
                    durationSeconds = 30
                )
            )
        ),

        // 9. Mantra Meditation
        MindfulnessExercise(
            id = "mantra",
            titleKey = "mantra",
            type = ExerciseType.TIMED,
            totalDurationSeconds = 60,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "Choose your phrase. Repeat it silently with each breath cycle. When a thought drifts away, notice it gently and return.\n\n• Inhale: Peace / Exhale: I release\n• Inhale: I am here / Exhale: Right now\n• This moment passes",
                    instructionPl = "Wybierz swoją frazę. Powtarzaj ją mentalnie z każdym cyklem oddechu. Gdy myśl odpłynie, zauważ to łagodnie i wróć.\n\n• Wdech: Spokój / Wydech: Uwalniam\n• Wdech: Jestem tu / Wydech: Teraz\n• Ten moment mija"
                )
            )
        ),

        // 10. Counting Backwards
        MindfulnessExercise(
            id = "counting_back",
            titleKey = "counting",
            type = ExerciseType.INTERACTIVE,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "Count backwards from 100, subtracting 7 each time.\n100 → 93 → 86 → 79 → 72...\n\nType each number as you go. This forces your prefrontal cortex to engage, breaking the anxiety loop.",
                    instructionPl = "Licz wstecz od 100, odejmując za każdym razem 7.\n100 → 93 → 86 → 79 → 72...\n\nWpisz każdą liczbę. To zmusza korę przedczołową do zaangażowania, przerywając pętlę lękową.",
                    inputType = StepInputType.NUMERIC
                )
            )
        ),

        // 11. Body Scan
        MindfulnessExercise(
            id = "body_scan",
            titleKey = "body_scan",
            type = ExerciseType.TIMED,
            totalDurationSeconds = 120,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "FEET & CALVES: Notice your toes and soles. Are they cold? Warm? Release any tension.",
                    instructionPl = "STOPY I ŁYDKI: Zauważ palce stóp i podeszwy. Zimne? Ciepłe? Uwolnij napięcie.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "HIPS & ABDOMEN: Is your belly tight? Let a breath fill it. Release.",
                    instructionPl = "MIEDNICA I BRZUCH: Czy brzuch jest zaciśnięty? Wpuść w niego oddech. Uwolnij.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "CHEST & BACK: Notice your chest rising and falling. Drop your shoulders 2 cm downward.",
                    instructionPl = "KLATKA I PLECY: Zauważ klatkę unoszącą się i opadającą. Opuść barki 2 cm w dół.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "HANDS & ARMS: Are your hands clenched? Open them. Feel your palms.",
                    instructionPl = "DŁONIE I RAMIONA: Czy masz zaciśnięte ręce? Otwórz je. Poczuj swoje dłonie.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "JAW & FOREHEAD: Unclench your jaw. Drop your tongue. Smooth your forehead. Relax your eyes.",
                    instructionPl = "SZCZĘKA I CZOŁO: Rozluźnij zaciśnięte zęby. Opuść język. Wygładź czoło. Rozluźnij oczy.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "Whole body at once. Feel the floor, the chair. You are grounded, safe, present.",
                    instructionPl = "Całe ciało naraz. Poczuj podłogę, krzesło. Jesteś uziemiony, bezpieczny, obecny.",
                    durationSeconds = 20
                )
            )
        ),

        // 12. ABC Analysis
        MindfulnessExercise(
            id = "abc_analysis",
            titleKey = "abc",
            type = ExerciseType.INTERACTIVE,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "A — ACTIVATING EVENT: What objectively happened? Describe only the facts, no interpretation. (e.g., 'Someone didn\'t reply to my message')",
                    instructionPl = "A — ZDARZENIE AKTYWUJĄCE: Co obiektywnie się wydarzyło? Opisz tylko fakty, bez interpretacji. (np. 'Ktoś nie odpisał na moją wiadomość')",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "B — BELIEFS: What did you automatically think about this event? (e.g., 'They don\'t care about me, I ruin everything')",
                    instructionPl = "B — PRZEKONANIA: Co automatycznie pomyślałeś o tym zdarzeniu? (np. 'Ma mnie gdzieś, znowu wszystko psuję')",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "C — CONSEQUENCES: What emotion appeared (0–100%)? What behavior followed? Recognize: it was B that created C, not A.",
                    instructionPl = "C — KONSEKWENCJE: Jaka emocja się pojawiła (0–100%)? Jakie zachowanie nastąpiło? Zauważ: to B wywołało C, nie A.",
                    inputType = StepInputType.TEXT
                )
            )
        ),

        // 13. Cognitive Restructuring
        MindfulnessExercise(
            id = "cognitive_restructuring",
            titleKey = "restructuring",
            type = ExerciseType.INTERACTIVE,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "THE HOT THOUGHT: What is the dominant automatic thought right now?",
                    instructionPl = "GORĄCA MYŚL: Jaka jest dominująca automatyczna myśl teraz?",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "COGNITIVE DISTORTION: Name it — All-or-nothing thinking? Catastrophizing? Mind reading? Labeling? Personalization?",
                    instructionPl = "ZNIEKSZTAŁCENIE: Nazwij je — Myślenie czarno-białe? Katastrofizowanie? Czytanie w myślach? Etykietowanie? Personalizacja?",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "EVIDENCE FOR vs AGAINST: What hard evidence supports this thought? What objective facts challenge it?",
                    instructionPl = "DOWODY ZA vs PRZECIW: Jakie twarde dowody wspierają tę myśl? Jakie obiektywne fakty ją podważają?",
                    inputType = StepInputType.TEXT
                ),
                ExerciseStep(
                    instructionEn = "BALANCED THOUGHT: Write a realistic, self-supporting sentence that integrates all the facts. What would you say to your best friend?",
                    instructionPl = "ZRÓWNOWAŻONA MYŚL: Sformułuj realistyczne, wspierające zdanie łączące wszystkie fakty. Co powiedziałbyś najlepszemu przyjacielowi?",
                    inputType = StepInputType.TEXT
                )
            )
        ),

        // 14. Mindful Detail Registration
        MindfulnessExercise(
            id = "detail_registration",
            titleKey = "details",
            type = ExerciseType.REFLECTIVE,
            totalDurationSeconds = 90,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "Choose one completely ordinary object in front of you. A pen, a glass, your own hand. Study it like a scientist examining an unknown sample through a magnifying glass.",
                    instructionPl = "Wybierz jeden całkowicie zwyczajny przedmiot przed tobą. Długopis, szklanka, Twoja dłoń. Badaj go jak naukowiec przez lupę.",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "Notice 3 micro-scratches or shadows on its edges that you've never noticed before.",
                    instructionPl = "Zauważ 3 mikro-rysy lub cienie na krawędzi, których nigdy wcześniej nie zauważałeś.",
                    durationSeconds = 25
                ),
                ExerciseStep(
                    instructionEn = "Notice how light reflects from its surface. Trace the gradient from the brightest to the darkest point.",
                    instructionPl = "Zauważ, jak światło odbija się od jego powierzchni. Prześledź gradient od najjaśniejszego do najciemniejszego punktu.",
                    durationSeconds = 25
                ),
                ExerciseStep(
                    instructionEn = "Notice its texture, weight, temperature. You have never truly seen this object until now.",
                    instructionPl = "Zauważ jego fakturę, ciężar, temperaturę. Nigdy wcześniej naprawdę nie widziałeś tego przedmiotu.",
                    durationSeconds = 20
                )
            )
        ),

        // 15. Emotion Acceptance / Urge Surfing (ACT/DBT)
        MindfulnessExercise(
            id = "emotion_acceptance",
            titleKey = "acceptance",
            type = ExerciseType.TIMED,
            totalDurationSeconds = 180,
            steps = listOf(
                ExerciseStep(
                    instructionEn = "NAME IT TO TAME IT: Say aloud or think clearly: 'A strong craving / anxiety / anger has appeared in me.'",
                    instructionPl = "NAZWIJ, ABY OSWOIĆ: Powiedz głośno lub pomyśl wyraźnie: 'Pojawił się we mnie silny głód / lęk / złość.'",
                    durationSeconds = 20
                ),
                ExerciseStep(
                    instructionEn = "WHERE IN YOUR BODY? Find where this wave is strongest. Throat? Stomach? Hands? Just notice — don't fight it.",
                    instructionPl = "GDZIE W CIELE? Znajdź, gdzie ta fala jest najsilniejsza. Gardło? Żołądek? Dłonie? Tylko obserwuj — nie walcz.",
                    durationSeconds = 30
                ),
                ExerciseStep(
                    instructionEn = "THE OCEAN WAVE: This impulse is like a wave. It naturally rises, reaches its peak (usually 3–5 minutes), then falls and fades. You don't have to fight it or drown in it — just stand on your board and watch it pass.",
                    instructionPl = "FALA OCEANU: Ten impuls to fala. Naturalnie rośnie, osiąga szczyt (zwykle po 3–5 minutach), potem opada i gaśnie. Nie musisz z nią walczyć — stań na desce i obserwuj, jak przepływa.",
                    durationSeconds = 130
                )
            )
        )
    )

    fun getRandomExercise(): MindfulnessExercise = allExercises.random()

    fun getExerciseById(id: String): MindfulnessExercise? = allExercises.find { it.id == id }
}
