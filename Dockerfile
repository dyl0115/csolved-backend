FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# 실행 스테이지
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# 컨테이너 실행 시 실행될 명령어
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]