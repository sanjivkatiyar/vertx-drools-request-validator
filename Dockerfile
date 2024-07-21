FROM openjdk:17-jdk-alpine
MAINTAINER Sanjiv Katiyar

EXPOSE 8888

COPY ./build/distributions/*.zip /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "vertx-drools-request-validator.jar"]