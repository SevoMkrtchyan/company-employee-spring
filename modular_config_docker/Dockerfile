#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY . /home/app
RUN mvn -f /home/app/web/src/main/resources/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/web/app/target/*-SNAPSHOT.jar /usr/local/lib/server.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/server.jar"]
