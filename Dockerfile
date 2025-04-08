# ---- Step 1: Build the app using Maven ----
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ---- Step 2: Run the built JAR ----
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/bookstore-0.0.1-SNAPSHOT.jar bookstore.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bookstore.jar"]

