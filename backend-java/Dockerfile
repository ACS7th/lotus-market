FROM gradle:jdk21-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle .

RUN gradle dependencies --no-daemon || true

COPY . .

RUN gradle build --no-daemon

#######

FROM openjdk:21-jdk-slim

COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 19999

CMD ["java", "-jar", "app.jar"]

