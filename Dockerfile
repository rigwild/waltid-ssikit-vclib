FROM openjdk:17-jdk-slim as buildstage
COPY ./ /
# Use Gradle so Docker can initiate a download and cache it
RUN ./gradlew --help
# Resolve dependencies to be cached
RUN ./gradlew dependencies
RUN ./gradlew build publishToMavenLocal
