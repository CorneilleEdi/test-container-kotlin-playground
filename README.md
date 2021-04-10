# Kotlin Testing Container

Testing is great. Now let's try it with [test-container](https://www.testcontainers.org/)

Tools:
- Kotlin
- JUnit5
- MongoDB
- KMongo
- Docker
- TestContainer

The first test `KmongoTest` will run with the local database and without TestContainer.

The second test `KmongoTestContainerTest` will use all the tools

```bash
./gradlew test
```