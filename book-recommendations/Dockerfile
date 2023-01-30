FROM gradle:7.5.1-jdk17-alpine AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

FROM  eclipse-temurin:17-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/book-recommendations-0.0.1-SNAPSHOT.jar /app/book-recommendations-jar.jar
ENTRYPOINT ["java","-jar","/app/book-recommendations-jar.jar"]
