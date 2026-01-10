# --- Stage 1: Build the JAR ---
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy the pom.xml and download dependencies (this layer is cached if pom doesn't change)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Create the Final Image ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy only the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port we moved to yesterday
EXPOSE 8001

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]