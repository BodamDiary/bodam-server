FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=target/*.jar
ARG PROFILES=prod
ARG ENV=prod
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}","-Dserver.env=${ENV}", "-jar", "app.jar"]