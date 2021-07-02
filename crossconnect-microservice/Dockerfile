# Build stage
FROM maven:3.6.3-jdk-11 as build

COPY ./ /home/multiverse
COPY ./pom.xml /home/multiverse

RUN mvn -f /home/multiverse/pom.xml -pl crossconnect-microservice -am clean install -Dmaven.test.skip=true


# Package stage
FROM openjdk:11.0.4-jre-slim

COPY --from=build /home/multiverse/crossconnect-microservice/target/crossconnect-microservice-fat.jar /opt/verticles/crossconnect-microservice.jar
COPY --from=build /home/multiverse/crossconnect-microservice/src/config/docker.json /opt/verticles/

EXPOSE 8089

WORKDIR /opt/verticles

ENTRYPOINT ["sh", "-c"]
CMD ["java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar crossconnect-microservice.jar -cluster -ha -conf docker.json"]