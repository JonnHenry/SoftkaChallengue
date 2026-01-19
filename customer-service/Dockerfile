# Stage 1: Build with Gradle + Java 17
FROM gradle:8-jdk17 AS build
WORKDIR /app

COPY --chown=gradle:gradle build.gradle settings.gradle gradlew ./
COPY --chown=gradle:gradle gradle ./gradle

RUN ./gradlew build -x test --warning-mode all || true

COPY --chown=gradle:gradle src ./src

RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Runtime image with Java 17
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8001

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
