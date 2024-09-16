#
# Build stage
#
#FROM maven:3.6.3-openjdk-8-slim AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml -Dmaven.test.skip=true clean package

#
# Package stage
#
FROM openjdk:8-jdk-alpine

# Timezone 보정
RUN apk add tzdata
RUN cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo "Asia/Seoul" > /etc/timezone

COPY target/*.jar /usr/local/lib/app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]

#FROM openjdk:8-jdk-alpine
#ADD target/appServer-0.0.1-SNAPSHOT.jar /root/app.jar
#ENTRYPOINT ["java", "-jar", "/root/app.jar"]