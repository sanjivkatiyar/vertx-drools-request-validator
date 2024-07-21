FROM openjdk:17-jdk-alpine
MAINTAINER Sanjiv Katiyar

EXPOSE 8888

COPY ./build/libs/vertx-drools-request-validator-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "vertx-drools-request-validator-1.0-SNAPSHOT.jar"]