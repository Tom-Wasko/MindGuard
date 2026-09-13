# MindGuard – Progress Tracking

_Aktualizowany automatycznie po każdym ukończonym zadaniu._

## Status projektu: 🟡 W trakcie implementacji

**Ostatnia aktualizacja:** 2026-09-13 20:21

---

## ✅ Ukończone

### Faza 0: Inicjalizacja
- [x] Analiza wymagań i redakcja promptu → `prompt_v2.md`
- [x] Dogłębny Implementation Plan → `implementation_plan.md`
- [x] Struktura katalogów projektu
- [x] `settings.gradle.kts` – konfiguracja projektu
- [x] `gradle/libs.versions.toml` – version catalog
- [x] `build.gradle.kts` (root) – pluginy
- [x] `app/build.gradle.kts` – zależności, BuildConfig, KSP
- [x] `gradle/wrapper/gradle-wrapper.properties` – Gradle 8.7
- [x] `AndroidManifest.xml` – permissions, services
- [x] `res/xml/locales_config.xml` – PL/EN
- [x] `res/xml/accessibility_service_config.xml`
- [x] `res/values/strings.xml` – pełne tłumaczenia EN
- [x] `res/values-pl/strings.xml` – pełne tłumaczenia PL
- [x] `res/values/themes.xml`
- [x] `.agents/android_rules.md` – reguły architektoniczne
- [x] `.agents/ProgressTracking.md` – ten plik

### Faza 1: Core + Theme + Onboarding (W trakcie)
- [x] `core/ui/theme/Color.kt` – paleta "Midnight Study"
- [x] `core/ui/theme/Type.kt` – typografia
- [x] `core/ui/theme/Theme.kt` – MindGuardTheme
- [x] `core/ui/theme/RetroEffects.kt` – efekty retro
- [x] `core/data/MindGuardConverters.kt` – Room converters
- [x] `core/data/MindGuardDatabase.kt` – Room database
- [x] `core/di/AppModule.kt` – Koin DI
- [x] `onboarding/data/entity/UserProfileEntity.kt`
- [x] `onboarding/data/dao/UserProfileDao.kt`
- [x] `onboarding/data/repository/OnboardingRepositoryImpl.kt`
- [x] `onboarding/domain/model/UserProfile.kt`
- [x] `onboarding/domain/repository/OnboardingRepository.kt`
- [x] `onboarding/domain/usecase/SaveUserProfileUseCase.kt`
- [ ] `onboarding/ui/OnboardingScreen.kt`
- [ ] `onboarding/ui/OnboardingViewModel.kt`
- [ ] `onboarding/ui/OnboardingUiState.kt`
- [x] Shared components: `core/ui/components/`
- [x] `mentor/data/entity/ChatMessageEntity.kt`
- [x] `mentor/data/dao/ChatMessageDao.kt`
- [x] `mindfulness/data/MindfulnessLogEntity.kt`
- [x] `mindfulness/data/MindfulnessLogDao.kt`
- [x] `cognitive/data/CognitiveTaskResultEntity.kt`

### Faza 2: Mindfulness Startup Screen (W trakcie)
- [x] `mindfulness/domain/model/MindfulnessExercise.kt`
- [x] `mindfulness/data/MindfulnessExerciseProvider.kt` (15 technik)
- [x] `mindfulness/domain/usecase/GetRandomExerciseUseCase.kt`
- [ ] `mindfulness/ui/MindfulnessScreen.kt`
- [ ] `mindfulness/ui/MindfulnessViewModel.kt`
- [ ] `mindfulness/ui/components/BreathingAnimation.kt`
- [ ] `mindfulness/ui/components/GroundingSteps.kt`
- [ ] `mindfulness/ui/components/BodyScanVisual.kt`
- [ ] `mindfulness/ui/components/CountdownTimer.kt`

### App Core (W trakcie)
- [x] `app/MindGuardApplication.kt`
- [x] `app/MainActivity.kt`
- [x] `app/navigation/NavGraph.kt`

---

## 🔄 W trakcie

- OnboardingScreen + ViewModel + UiState
- MindfulnessScreen + ViewModel + components
- GeminiMentorService
- MainMenuScreen
- Cognitive Tasks (domain, ui)
- Panic Protocol (service, screen)
- Gradle wrapper JAR (do pobierania przez Android Studio)

---

## 📋 Do zrobienia

- [ ] `local.properties` z GEMINI_API_KEY
- [ ] `task_bank.json` – bank zadań matematycznych
- [ ] `proguard-rules.pro`
- [ ] Ikona aplikacji (adaptive icon)
- [ ] Gradle wrapper JAR (gradlew, gradlew.bat)
- [ ] Final build verification

---

## 🐛 Znane problemy / TODO techniczne

1. **Gradle wrapper JAR** – plik binarny `gradle-wrapper.jar` musi być pobrany przez Android Studio lub `gradle wrapper` command
2. **GEMINI_API_KEY** – wymaga wygenerowania na AI Studio i wpisania do `local.properties`
3. **Ikony** – placeholdery, do zastąpienia przez wygenerowane ikony AI
4. **Fonty** – JetBrains Mono i Plus Jakarta Sans do pobrania z Google Fonts i dodania do `res/font/`

---

## 📊 Metryki

| Kategoria | Pliki | Status |
|-----------|-------|--------|
| Gradle/Config | 5 | ✅ Kompletne |
| Resources (XML/strings) | 5 | ✅ Kompletne |
| Room/Data | 8 | ✅ Kompletne |
| Domain | 5 | ✅ Kompletne |
| UI Screens | 0/7 | 🔄 W trakcie |
| AI/Mentor | 0/4 | 📋 Do zrobienia |
| Panic Protocol | 0/3 | 📋 Do zrobienia |
| Cognitive Tasks | 0/4 | 📋 Do zrobienia |
