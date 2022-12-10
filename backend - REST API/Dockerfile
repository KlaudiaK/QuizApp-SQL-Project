FROM amazoncorretto:17 as builder
WORKDIR /opt/docker
COPY gradle/ gradle/
COPY gradlew  gradlew
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY ./src ./src
RUN  ./gradlew clean build -x test

FROM amazoncorretto:17 as base
ARG JAR_FILE=build/libs/*.jar
WORKDIR /opt/docker
COPY --from=builder /opt/docker/build/libs/*.jar /opt/docker/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/docker/*.jar"]
