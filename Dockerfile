FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 기본값 설정
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_ENV=prod
EXPOSE 443 80

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-Dserver.env=${SERVER_ENV}", "-jar", "app.jar"]