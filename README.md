# TowertexErste
experimental architecture demo
features:
- separated concerns
    - ErsteApi module to handle network
    - ErsteModel module to handle business logic
- ErsteApi module
    - test coverage via JUnit
    - Ktor
    - services separated via delegation
- ErsteModel module
    - test coverage via AndroidInstrumentation
    - persistency via Room
    - SigleSourceOfTruth architecture
    - repositories separated via delegation
- app module
    - test coverage via MockK
    - dependency injection via Koin
    - ResourceRepository pattern
    - MVVM
    - Compose
    - navigation via NavHost
    - Jetpack paging library