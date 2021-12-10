FROM openjdk:11.0.7-jdk-slim

COPY . .

RUN mvn clean package

COPY target/company-employee-spring-0.0.1-SNAPSHOT.jar /company-employee-spring.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "/company-employee-spring.jar"]
