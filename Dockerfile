FROM maven:3.8.4-openjdk-17

WORKDIR /app

COPY . .

RUN chmod +x mvnw

EXPOSE 8080

CMD [ "./mvnw", "spring-boot:run" ]