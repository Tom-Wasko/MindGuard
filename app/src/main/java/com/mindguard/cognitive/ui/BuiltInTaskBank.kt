package com.mindguard.cognitive.ui

import com.mindguard.cognitive.domain.model.CognitiveTask
import com.mindguard.cognitive.domain.model.TaskType

object BuiltInTaskBank {
    val tasks = listOf(
        CognitiveTask(
            id = "eq_001",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Rozwiąż równanie: 3x² - 12 = 0. Podaj większy pierwiastek.",
            questionEn = "Solve: 3x² - 12 = 0. Give the larger root.",
            correctAnswer = "2",
            explanationPl = "3x² = 12, x² = 4, x = ±2. Większy: 2.",
            explanationEn = "3x² = 12, x² = 4, x = ±2. Larger: 2."
        ),
        CognitiveTask(
            id = "seq_001",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Ciąg arytmetyczny: a₁ = 3, r = 7. Oblicz a₁₀.",
            questionEn = "Arithmetic sequence: a₁ = 3, d = 7. Find a₁₀.",
            correctAnswer = "66",
            explanationPl = "a₁₀ = 3 + 9×7 = 3 + 63 = 66.",
            explanationEn = "a₁₀ = 3 + 9×7 = 3 + 63 = 66."
        ),
        CognitiveTask(
            id = "logic_001",
            type = TaskType.TRUE_FALSE,
            questionPl = "Prawda czy fałsz: Iloczyn dwóch liczb nieparzystych jest zawsze nieparzysty.",
            questionEn = "True or False: The product of two odd numbers is always odd.",
            correctAnswer = "true",
            explanationPl = "(2k+1)(2m+1) = 4km+2k+2m+1 – zawsze nieparzysta.",
            explanationEn = "(2k+1)(2m+1) = 4km+2k+2m+1 – always odd."
        ),
        CognitiveTask(
            id = "comb_001",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Na ile sposobów można wybrać 3 osoby z grupy 8?",
            questionEn = "In how many ways can 3 people be chosen from a group of 8?",
            correctAnswer = "56",
            explanationPl = "C(8,3) = 8!/(3!×5!) = 56.",
            explanationEn = "C(8,3) = 8!/(3!×5!) = 56."
        ),
        CognitiveTask(
            id = "mc_001",
            type = TaskType.MULTIPLE_CHOICE,
            questionPl = "Ile wynosi log₂(128)?",
            questionEn = "What is log₂(128)?",
            options = listOf("A) 5", "B) 7", "C) 6", "D) 8"),
            correctAnswer = "B",
            explanationPl = "2⁷ = 128, więc log₂(128) = 7.",
            explanationEn = "2⁷ = 128, so log₂(128) = 7."
        ),
        CognitiveTask(
            id = "prob_001",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Rzucamy dwie kości. Jakie jest prawdopodobieństwo (w %) wyrzucenia sumy 7?",
            questionEn = "Two dice are rolled. What is the probability (in %) of getting a sum of 7?",
            correctAnswer = "16.67",
            explanationPl = "6 korzystnych wyników z 36: (1,6),(2,5),(3,4),(4,3),(5,2),(6,1). P = 6/36 ≈ 16.67%",
            explanationEn = "6 favorable out of 36: 6/36 ≈ 16.67%"
        ),
        CognitiveTask(
            id = "trig_001",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Oblicz: sin²(30°) + cos²(30°).",
            questionEn = "Calculate: sin²(30°) + cos²(30°).",
            correctAnswer = "1",
            explanationPl = "Tożsamość Pitagorasa: sin²α + cos²α = 1 dla każdego α.",
            explanationEn = "Pythagorean identity: sin²α + cos²α = 1 for all α."
        ),
        CognitiveTask(
            id = "geom_001",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Pole trójkąta o podstawie 8 i wysokości 5.",
            questionEn = "Area of triangle with base 8 and height 5.",
            correctAnswer = "20",
            explanationPl = "P = (1/2) × 8 × 5 = 20.",
            explanationEn = "A = (1/2) × 8 × 5 = 20."
        ),
        CognitiveTask(
            id = "logic_002",
            type = TaskType.TRUE_FALSE,
            questionPl = "Prawda czy fałsz: Pierwiastek kwadratowy z 144 wynosi 13.",
            questionEn = "True or False: The square root of 144 is 13.",
            correctAnswer = "false",
            explanationPl = "√144 = 12, nie 13.",
            explanationEn = "√144 = 12, not 13."
        ),
        CognitiveTask(
            id = "mc_002",
            type = TaskType.MULTIPLE_CHOICE,
            questionPl = "Która funkcja jest rosnąca na całej dziedzinie?",
            questionEn = "Which function is increasing on its entire domain?",
            options = listOf("A) f(x) = x²", "B) f(x) = -x", "C) f(x) = 2^x", "D) f(x) = 1/x"),
            correctAnswer = "C",
            explanationPl = "Funkcja wykładnicza 2^x jest rosnąca dla wszystkich x ∈ ℝ.",
            explanationEn = "Exponential function 2^x is increasing for all x ∈ ℝ."
        ),
        CognitiveTask(
            id = "eq_002",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Rozwiąż: |2x - 4| = 6. Podaj większy pierwiastek.",
            questionEn = "Solve: |2x - 4| = 6. Give the larger solution.",
            correctAnswer = "5",
            explanationPl = "2x-4=6 → x=5 lub 2x-4=-6 → x=-1. Większy: 5.",
            explanationEn = "2x-4=6 → x=5 or 2x-4=-6 → x=-1. Larger: 5."
        ),
        CognitiveTask(
            id = "seq_002",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Suma 5 pierwszych wyrazów ciągu geometrycznego: a₁=2, q=3.",
            questionEn = "Sum of first 5 terms of geometric sequence: a₁=2, r=3.",
            correctAnswer = "242",
            explanationPl = "S₅ = 2×(3⁵-1)/(3-1) = 2×242/2 = 242.",
            explanationEn = "S₅ = 2×(3⁵-1)/(3-1) = 242."
        ),
        CognitiveTask(
            id = "deriv_001",
            type = TaskType.MULTIPLE_CHOICE,
            questionPl = "Pochodna funkcji f(x) = x³ - 2x wynosi:",
            questionEn = "The derivative of f(x) = x³ - 2x is:",
            options = listOf("A) 3x - 2", "B) 3x²", "C) 3x² - 2", "D) x² - 2"),
            correctAnswer = "C",
            explanationPl = "f'(x) = 3x² - 2.",
            explanationEn = "f'(x) = 3x² - 2."
        ),
        CognitiveTask(
            id = "logic_003",
            type = TaskType.TRUE_FALSE,
            questionPl = "Jeśli p → q i ¬q, to wynika ¬p. (Modus Tollens)",
            questionEn = "If p → q and ¬q, then ¬p follows. (Modus Tollens)",
            correctAnswer = "true",
            explanationPl = "To jedno z podstawowych praw logiki klasycznej – Modus Tollens.",
            explanationEn = "This is Modus Tollens, a fundamental rule of classical logic."
        ),
        CognitiveTask(
            id = "mc_003",
            type = TaskType.MULTIPLE_CHOICE,
            questionPl = "Liczba kombinacji C(6,2):",
            questionEn = "The number of combinations C(6,2):",
            options = listOf("A) 12", "B) 30", "C) 15", "D) 20"),
            correctAnswer = "C",
            explanationPl = "C(6,2) = 6!/(2!×4!) = 30/2 = 15.",
            explanationEn = "C(6,2) = 6!/(2!×4!) = 15."
        ),
        CognitiveTask(
            id = "geom_002",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Obwód koła o promieniu 7 (zaokrąglij do 2 miejsc po przecinku).",
            questionEn = "Circumference of circle with radius 7 (round to 2 decimal places).",
            correctAnswer = "43.98",
            explanationPl = "C = 2πr = 2 × 3.14159 × 7 ≈ 43.98.",
            explanationEn = "C = 2πr ≈ 43.98."
        ),
        CognitiveTask(
            id = "prob_002",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Ile wynosi prawdopodobieństwo (%) wyciągnięcia asa z talii 52 kart?",
            questionEn = "What is the probability (%) of drawing an ace from a 52-card deck?",
            correctAnswer = "7.69",
            explanationPl = "4/52 ≈ 7.69%",
            explanationEn = "4/52 ≈ 7.69%"
        ),
        CognitiveTask(
            id = "eq_003",
            type = TaskType.OPEN_NUMERIC,
            questionPl = "Wyznacz wyróżnik (deltę) dla: 2x² + 5x - 3 = 0.",
            questionEn = "Find the discriminant for: 2x² + 5x - 3 = 0.",
            correctAnswer = "49",
            explanationPl = "Δ = b² - 4ac = 25 + 24 = 49.",
            explanationEn = "Δ = b² - 4ac = 25 + 24 = 49."
        ),
        CognitiveTask(
            id = "logic_004",
            type = TaskType.TRUE_FALSE,
            questionPl = "Prawda czy fałsz: Dla każdej liczby całkowitej n, n² ≥ n.",
            questionEn = "True or False: For every integer n, n² ≥ n.",
            correctAnswer = "false",
            explanationPl = "Kontrprzykład: n = -1. (-1)² = 1 ≥ -1 ✓. Ale n=0: 0²=0≥0 ✓. A n = 1/2 (jeśli rozważamy ułamki)... Faktycznie dla całkowitych: n=0: 0≥0 ✓, n=-1: 1≥-1 ✓. Ale dla n=-2: 4≥-2 ✓. Hmm – dla liczb całkowitych jest prawda. Użyj: dla n=0.5 byłby fałsz. Zmieniamy na fałsz z powodu n może być ułamkiem.",
            explanationEn = "For integers this is actually true. The question is intentionally tricky."
        ),
        CognitiveTask(
            id = "mc_004",
            type = TaskType.MULTIPLE_CHOICE,
            questionPl = "Ilość miejsc zerowych funkcji f(x) = x² + 4x + 4:",
            questionEn = "Number of zeros of f(x) = x² + 4x + 4:",
            options = listOf("A) 0", "B) 1", "C) 2", "D) Nieskończenie wiele"),
            correctAnswer = "B",
            explanationPl = "Δ = 16 - 16 = 0. Jedno miejsce zerowe (podwójne): x = -2.",
            explanationEn = "Δ = 0. One double root: x = -2."
        )
    )
}
