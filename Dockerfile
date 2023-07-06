FROM gradle AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:22-jdk
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

