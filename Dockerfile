FROM openjdk:17-jdk-alpine
MAINTAINER Sanjiv Katiyar
ADD build/distributions/app/lib/*.jar app.jar
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]