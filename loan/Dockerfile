#Start with a base image containing Java runtime
FROM openjdk:17-alpine as build

#Information around who maintains the image
MAINTAINER a_meydnl

# Add the application's jar to the container
COPY target/loan-0.0.1-SNAPSHOT.jar loan-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/loan-0.0.1-SNAPSHOT.jar"]