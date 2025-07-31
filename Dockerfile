# Użyj obrazu Maven do budowania aplikacji
FROM maven:3.8.6-openjdk-17-slim AS build

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj plik pom.xml i zależności do kontenera
COPY pom.xml .

# Pobierz zależności Maven (bez budowania aplikacji)
RUN mvn dependency:go-offline

# Skopiuj całą aplikację
COPY src /app/src

# Zbuduj aplikację
RUN mvn clean package -DskipTests

# Użyj obrazu z JDK do uruchomienia aplikacji
FROM openjdk:17-jdk-slim

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj skompilowany plik JAR z etapu budowania
COPY --from=build /app/target/*.jar app.jar

# Otwórz port 8080
EXPOSE 8080

# Uruchom aplikację
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
