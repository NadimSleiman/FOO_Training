FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY build/libs/training-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your application runs
EXPOSE 8080

# Set the command to run your application
CMD ["java", "-jar", "app.jar"]
