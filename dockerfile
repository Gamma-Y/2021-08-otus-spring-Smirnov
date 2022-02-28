FROM maven:3-openjdk-11 AS build

COPY spring_server/library_spring_REST/src /usr/app/src
COPY spring_server/library_spring_REST/pom.xml /usr/app

EXPOSE 8080

RUN mvn -f /usr/app/pom.xml clean package

ENTRYPOINT ["java","-jar","/usr/app/target/library_server.jar"]