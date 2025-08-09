# Stage 1: Build with Maven
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run with JDK
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${MODE}", "app.jar"]
