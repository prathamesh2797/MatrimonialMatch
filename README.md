# MatrimonialMatchApp

This is a sample Android app that mimics a basic matrimonial-style platform. The idea was to fetch user profiles from an API, show them in a card format with Accept/Decline options, store them locally, and keep things working even when offline.

## Project Setup
1. Clone the repo:
2. Open it in Android Studio
3. Build requirements:
   - Min SDK: 26
   - Compile SDK: 35
   - Kotlin: 2.0.21
   - AGP: 8.10.1
4. Run on emulator or device

## Library list with justifications
- **Retrofit**: Retrofit is battle-tested, well-documented, and integrates perfectly with Gson, Coroutines, and even custom interceptors. It handles edge cases, retries, and error parsing better than most.
- **Hilt**: Hilt simplifies dependency injection in Android, making the code cleaner and more maintainable. It reduces boilerplate code and integrates seamlessly with ViewModels and other Android components.
- **StateFlow + Coroutines**: StateFlow is a powerful way to manage UI state in a reactive manner. It allows for easy updates and ensures that the UI is always in sync with the data layer. Coroutines make asynchronous programming straightforward and efficient.
- **OkHttp**: We needed to simulate real-world network issues where 30% of API calls fail. A custom OkHttp Interceptor allows us to intercept every request and randomly throw an error — all within the app, without needing to mock anything externally.
- **Coil**: Coil is a lightweight image loading library that works seamlessly with Kotlin Coroutines. It supports caching, resizing, and transformations, making it perfect for loading user profile images efficiently.
- **Room**: Room provides an abstraction layer over SQLite, making database operations more straightforward and type-safe. It integrates well with LiveData and Coroutines, allowing for reactive data updates in the UI.
- **Jetpack Compose**: Compose is the future of Android UI development. It allows for declarative UI programming, making it easier to build and maintain complex UIs. It integrates seamlessly with StateFlow and ViewModels, ensuring a reactive and responsive UI.


## Architecture explanation
This project follows **Modern Android Development (MAD)** principles with a **Clean MVVM architecture**.
- **UI Layer** → Built using Jetpack Compose + ViewModel + StateFlow
- **Domain Layer** → Holds business logic, models, use cases, and repository interfaces
- **Data Layer** → Manages API (Retrofit), local DB (Room), and mapping logic
- **DI Layer** → Hilt is used to inject dependencies cleanly
- **Offline-first** → Room ensures the app is usable without internet

This layered setup makes the app scalable, testable, and easy to maintain.

## Justification for added fields
Have added Education and Religion fields to the user profile. These fields are common in matrimonial profiles and provide more context about potential matches, enhancing the user experience.
- **Education** – shows academic background (e.g. MBA, B.Tech)
- **Religion** – useful in matchmaking in Indian context

These were generated randomly and injected into the user data.

## Match score logic description
Each profile displays a match score (0–100), calculated locally using this logic:
- **City Match**: +50 points if user's city matches current user
- **Age Proximity**: Score = max(0, 50 - (age difference × 5))
This logic rewards nearby and similar-aged profiles with a higher score.

## Offline and error handling strategy
- All profiles are stored in a Room database after API fetch
- On app restart or no network, data is loaded from DB
- A custom FakeFlakyNetworkInterceptor randomly fails 30% of API calls to simulate real-world unstable conditions
- A manual retry logic retries failed calls up to 3 times
- Graceful fallback to cached DB and error messages if everything fails

## Design Constraint Handling
- In case profile images can’t be shown (legal reason or anything else), the UI switches to show a grey box with the first letter of the person’s name.
- This avoids blank areas and keeps the layout usable. A config flag (SHOW_PROFILE_IMAGES) controls this behavior. In future we can maintain this flag in Over The Air (OTA) updates or a remote config.

## Reflection
- If I had more time, I would’ve added filters — like letting the user choose preferred education, religion, age range, etc. It would’ve made the experience more personalized and aligned with what people actually look for in matrimonial apps.