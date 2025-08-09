# Stage 1: Build with Maven
FROM --platform=linux/amd64 maven:3.8.5-openjdk-17 AS builder

# Install node
ARG NODE_VERSION=20.12.0
RUN curl https://nodejs.org/dist/v$NODE_VERSION/node-v$NODE_VERSION-linux-x64.tar.gz | tar -xz -C /usr/local --strip-components 1

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Copy Angular config files
COPY package.json .
COPY package-lock.json .
COPY angular.json .
COPY tsconfig.app.json .
COPY tsconfig.json .
# Copy static files
COPY public ./public

# Build spring (Angular is build throughout the spring build)
RUN mvn clean package -DskipTests

# Stage 2: Run with JDK
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/ui-service-0.0.1.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
