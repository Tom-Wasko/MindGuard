# MindGuard – Android Project

## Opis
**MindGuard** to natywna aplikacja Android służąca jako „forteca uważności" wspierająca osoby w radzeniu sobie z nałogami i kryzysami emocjonalnymi poprzez techniki CBT/DBT/ACT, interwencje mindfulness i mentoring AI.

## Stack technologiczny

| Technologia | Wersja |
|-------------|--------|
| Kotlin | 2.0.21 |
| Jetpack Compose BOM | 2024.09.03 |
| Android Gradle Plugin | 8.5.2 |
| Gradle | 8.7 |
| Room | 2.6.1 |
| Koin | 3.5.6 |
| Gemini AI SDK | 0.9.0 |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 35 (Android 15) |

## Architektura
MVVM + Clean Architecture (UI → Domain → Data)

## Konfiguracja klucza API Gemini

1. Wejdź na [Google AI Studio](https://aistudio.google.com/)
2. Kliknij "Get API Key" → "Create API Key"
3. Skopiuj klucz
4. Otwórz plik `local.properties` i wstaw:
   ```
   GEMINI_API_KEY=TwójKluczTutaj
   ```

## Uruchomienie w Android Studio

1. Otwórz folder `MindGuard` w Android Studio
2. Poczekaj na synchronizację Gradle
3. Dodaj klucz Gemini do `local.properties`
4. Uruchom na emulatorze lub urządzeniu (min. Android 8.0)

## Struktura projektu

```
MindGuard/
├── .agents/
│   ├── android_rules.md      # Reguły architektoniczne
│   └── ProgressTracking.md   # Postęp implementacji
├── app/
│   ├── src/main/
│   │   ├── java/com/mindguard/
│   │   │   ├── app/          # Application, MainActivity, Navigation
│   │   │   ├── core/         # Theme, Database, DI, Utils
│   │   │   ├── onboarding/   # Onboarding feature
│   │   │   ├── mindfulness/  # Mindfulness startup screen
│   │   │   ├── panic/        # Panic Protocol
│   │   │   ├── cognitive/    # Math & Logic tasks
│   │   │   ├── mentor/       # AI Mentor (Gemini)
│   │   │   └── ui/           # Shared screens (Main Menu, Settings)
│   │   ├── res/
│   │   │   ├── values/       # EN strings
│   │   │   └── values-pl/    # PL strings
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   ├── libs.versions.toml    # Version catalog
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle.kts
├── settings.gradle.kts
├── local.properties          # API keys (not in Git)
├── prompt_v2.md              # Zredagowany prompt
└── README.md
```

## Funkcje aplikacji

### 🧘 Mindfulness Startup Screen
15 technik mindfulness (CBT/DBT/ACT) wyświetlanych losowo przy każdym uruchomieniu jako ekran blokujący.

### 🆘 Panic Protocol
200-sekundowy hard lock z technikami oddechowymi. Zawsze dostępny przycisk awaryjny (112/aparat).

### 🤖 AI Mentor
Gemini 1.5 Flash z systemowym promptem klinicznym (Dialog Motywujący, Dialektyczna Abstynencja).

### 🔢 Math & Logic
Bank 20+ zadań na poziomie matury rozszerzonej odciągających uwagę od głodu nałogowego.

## Licencja
Projekt prywatny / w trakcie tworzenia.
