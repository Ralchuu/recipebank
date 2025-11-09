
# Maven Build (Java 21)
FROM maven:3.9.7-eclipse-temurin-21 AS build

# Copy source code and pom.xml
COPY pom.xml /home/app/
COPY src /home/app/src/

# Build the project
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Runtime Image (Java 21)
FROM eclipse-temurin:21-jre

# Copy the built jar from build stage
COPY --from=build /home/app/target/rauli-0.0.1-SNAPSHOT.jar /usr/local/lib/raulibookstore.jar

# Expose the port your app runs on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "/usr/local/lib/raulibookstore.jar"]
