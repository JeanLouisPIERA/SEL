### BUILD image
FROM maven:3.6.3-jdk-11 as builder
# create app folder for sources
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
#Download all required dependencies into one layer
RUN mvn -B dependency:resolve dependency:resolve-plugins
#Copy source code
COPY src /build/src
# Build application
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:11-jre-slim
COPY --from=builder /build/target/microselreferentiels-0.0.1-SNAPSHOT.jar /build/microselreferentiels-0.0.1-SNAPSHOT.jar
WORKDIR /build

#Run the application
CMD java -jar microselreferentiels-0.0.1-SNAPSHOT.jar