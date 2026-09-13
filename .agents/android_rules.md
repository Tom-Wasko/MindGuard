# MindGuard – Agent Rules

## Architektura: MVVM + Clean Architecture

Każda feature-folder musi zawierać:
```
<feature>/
├── data/
│   ├── entity/         # Room @Entity
│   ├── dao/            # Room @Dao
│   └── repository/     # Repository implementations
├── domain/
│   ├── model/          # Domain models (czyste Kotlin data classes)
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Use cases (jedna odpowiedzialność)
└── ui/
    ├── <Feature>Screen.kt     # @Composable Screen function
    ├── <Feature>ViewModel.kt  # ViewModel extends ViewModel()
    ├── <Feature>UiState.kt    # sealed interface UiState
    └── components/            # Reusable composables dla tej feature
```

## Naming Conventions

| Typ | Konwencja | Przykład |
|-----|-----------|---------|
| Pakiet | `com.mindguard.<feature>.<layer>` | `com.mindguard.onboarding.ui` |
| ViewModel | `<Feature>ViewModel` | `OnboardingViewModel` |
| Screen | `<Feature>Screen` | `OnboardingScreen` |
| Entity | `<Feature>Entity` | `UserProfileEntity` |
| DAO | `<Feature>Dao` | `UserProfileDao` |
| Repository | `<Feature>Repository` | `OnboardingRepository` |
| UseCase | `<Verb><Noun>UseCase` | `SaveUserProfileUseCase` |
| UiState | `<Feature>UiState` | `OnboardingUiState` |

## i18n Requirements

- **NIGDY** nie używaj hardcoded stringów w UI
- Wszystkie stringi UI muszą być w `res/values/strings.xml` (EN) i `res/values-pl/strings.xml` (PL)
- W Compose: `stringResource(R.string.key_name)`
- Formatowane stringi: `stringResource(R.string.key_name, arg1, arg2)`
- Plurals: `pluralStringResource(R.plurals.key_name, count, count)`

## Edge-to-Edge UI

- `enableEdgeToEdge()` musi być wywołane w `MainActivity.onCreate()` PRZED `super.onCreate()`
- Każdy ekran pełnoekranowy musi obsługiwać `WindowInsets`
- Używaj `Scaffold(contentWindowInsets = WindowInsets.safeDrawing)` lub `Modifier.windowInsetsPadding()`
- Tła mogą rozciągać się pod status barem – treść musi mieć padding od insets

## Kotlin Code Style

- Używaj Kotlin idiomów: `when`, `let`, `apply`, `also`, `run`
- Extension functions dla mapowania: `Entity.toDomain()`, `Domain.toEntity()`
- UiState jako sealed interface z Loading, Success, Error
- ViewModels używają `viewModelScope.launch` dla coroutines
- Repository zwraca `Flow<T>` dla reaktywnych danych, `suspend fun` dla jednorazowych operacji

## Compose Best Practices

- Stateless composables gdzie możliwe (State Hoisting)
- Każdy Screen przyjmuje callback lambdy zamiast NavigationController
- Preview funkcje dla każdego komponentu (`@Preview`)
- Animacje: preferuj `AnimatedVisibility`, `animateContentSize`, `rememberInfiniteTransition`

## Error Handling

- Używaj `Result<T>` lub sealed `UiState` z `Error` state
- Timber lub `android.util.Log` dla logowania (nie println)
- Gemini AI errors: obsługuj `GenerativeAIException` z offline fallback

## Build Configuration

- Min SDK: 26 (Android 8.0 Oreo)
- Target SDK: 35 (Android 15)
- Kotlin: 2.0+
- Java compatibility: VERSION_17
- Room schema export: true (do app/schemas/)
- BuildConfig: GEMINI_API_KEY z local.properties

## Security

- NIGDY nie hardcoduj API keys w source code
- GEMINI_API_KEY wyłącznie przez `local.properties` → `BuildConfig`
- `.gitignore` musi zawierać `local.properties`
