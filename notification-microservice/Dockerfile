# Build stage
FROM maven:3.6.3-jdk-11 as build

COPY ./ /home/multiverse
COPY ./pom.xml /home/multiverse

RUN mvn -f /home/multiverse/pom.xml -pl notification-microservice -am clean install -Dmaven.test.skip=true


# Package stage
FROM openjdk:11.0.4-jre-slim

COPY --from=build /home/multiverse/notification-microservice/target/notification-microservice-fat.jar /opt/verticles/notification-microservice.jar
COPY --from=build /home/multiverse/notification-microservice/src/config/docker.json /opt/verticles/

EXPOSE 8086

WORKDIR /opt/verticles

ENTRYPOINT ["sh", "-c"]
CMD ["java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar notification-microservice.jar -cluster -ha -conf docker.json"]