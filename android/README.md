# NormalizeParkingLot-android
Normalize the Parking Lot (NPL) android repository

### How To Run
```
1. add local.properties
2. sync gradle
3. build & run
```
request @kick-snare `local.properties` that contains api key

### app architecture

```mermaid
flowchart TD
    A[app] --> B(data)
    A[app] --> G(presentation)
    G --> B
    G --> C{ui}
    C --> D[map]
    C --> E[favorite]
    C --> H[provider]
    C --> F[setting]
    B --> repository
    repository --> interface
    repository --> impl
    B --> di
    B --> remote
    remote --> service
    remote --> dto
    B --> local
    local --> dao
    local --> entity
```
with MVVM

### tech stacks
- Jetpack Compose
- Kotlin Coroutine
- Dagger Hilt
- FireStore
- Retrofit
- Coil
- etc...
