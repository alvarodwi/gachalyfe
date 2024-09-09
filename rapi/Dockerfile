# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY build/libs/rapi-0.0.1.jar /app/app.jar

# Run the jar file
CMD ["java", "-jar", "app.jar"]
