FROM openjdk:11-jdk

EXPOSE 9102

COPY maven/eurekaserver-0.0.1-SNAPSHOT.jar eureka-server.jar 

CMD ["java","-jar","config-server.jar"]
