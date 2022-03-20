#Start with a base image containing Java runtime
FROM openjdk:17-alpine as build

#Information around who maintains the image
MAINTAINER a_meydnl

# Add the application's jar to the container
COPY target/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/account-0.0.1-SNAPSHOT.jar"]