FROM maven:latest as builder
LABEL authors="henri"

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install

FROM openjdk:17

COPY --from=builder app/target/gateway-1.0.jar /app/gateway-1.0.jar
WORKDIR /app

CMD ["java", "-jar", "gateway-1.0.jar"]
