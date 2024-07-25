# Stage 1: Build the application
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
# Copy all src code & Build the application
COPY . .            
RUN gradle clean build --no-daemon       

# Stage 2: Create the runtime image
FROM openjdk:17
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
