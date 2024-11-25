FROM maven:3.9.9-eclipse-temurin-21-alpine

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

EXPOSE 8080

CMD mvn "spring-boot:run"