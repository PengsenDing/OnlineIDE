# Stage 1: Build with Maven
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with JDK
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/project-0.0.1.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${MODE}", "app.jar"]
