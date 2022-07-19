FROM openjdk:17-jdk-slim as buildstage

# Use Gradle so Docker can initiate a download and cache it
COPY ./gradle /gradle
COPY ./gradle.properties /gradle.properties
COPY ./gradlew /gradlew
COPY ./build.gradle.kts /build.gradle.kts
COPY ./settings.gradle.kts /settings.gradle.kts
RUN ./gradlew --help

# Resolve dependencies to be cached
RUN ./gradlew dependencies

COPY ./ /
RUN ./gradlew build publishToMavenLocal
