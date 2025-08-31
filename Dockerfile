# Stage 1: Build
FROM maven:eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/mindnova.jar ./mindnova.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","mindnova.jar"]