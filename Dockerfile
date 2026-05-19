FROM eclipse-temurin:17-jdk AS build

WORKDIR /root_dir

COPY gradle ./gradle
COPY gradle.properties .
COPY gradlew .
COPY settings.gradle.kts .
COPY app/src ./app/src
COPY app/build.gradle.kts ./app/


RUN chmod +x gradlew

RUN ./gradlew bootJar

FROM eclipse-temurin:17-jre

WORKDIR /root_dir

COPY --from=build /root_dir/app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

