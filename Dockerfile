FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

COPY ./project/pom.xml .
COPY ./project/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=builder /app/target/pruebatec-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
