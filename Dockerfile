FROM openjdk:17-jdk-slim as buildstage
COPY ./ /
RUN ./gradlew build publishToMavenLocal
