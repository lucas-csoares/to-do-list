FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17

EXPOSE 8080

COPY --from=build target/to-do-list-0.0.1-SNAPSHOT.jar app.jar
COPY --from=build src/main/resources/application.properties application.properties

LABEL authors="Lucas Soares"

ENTRYPOINT ["java", "-jar", "app.jar"]