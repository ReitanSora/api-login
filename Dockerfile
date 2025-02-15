FROM gradle:8.7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble --no-daemon

FROM openjdk:17-slim
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/api-login-0.0.1-SNAPSHOT.jar /api-login.jar
ENTRYPOINT ["java","-jar","/api-login.jar"]