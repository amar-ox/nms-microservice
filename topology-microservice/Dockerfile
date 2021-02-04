# Build stage
FROM maven:3.6.3-jdk-11 as build

COPY ./ /home/multiverse
COPY ./pom.xml /home/multiverse

RUN mvn -f /home/multiverse/pom.xml -pl topology-microservice -am clean install -Dmaven.test.skip=true


# Package stage
FROM openjdk:11.0.4-jre-slim

COPY --from=build /home/multiverse/topology-microservice/target/topology-microservice-fat.jar /opt/verticles/topology-microservice.jar
COPY --from=build /home/multiverse/topology-microservice/src/config/docker.json /opt/verticles/

EXPOSE 8085

WORKDIR /opt/verticles

ENTRYPOINT ["sh", "-c"]
CMD ["java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar topology-microservice.jar -cluster -ha -conf docker.json"]