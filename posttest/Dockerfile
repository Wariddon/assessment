FROM amazoncorretto:17.0.9-alpine3.18

WORKDIR /app
ADD . .
RUN ["./gradlew","clean","build","wrapper","bootJar"]
ENTRYPOINT ["java", "-jar", "build/libs/postTest-0.0.1-SNAPSHOT.jar"]