FROM maven:3.9.9-eclipse-temurin-21-alpine

WORKDIR /app

COPY . .

RUN chmod +x mvnw

EXPOSE 8080

CMD [ "./mvnw", "spring-boot:run" ]