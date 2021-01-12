# Build stage
FROM maven:3.6.3-openjdk-8 as build

COPY ./ /home/multiverse
COPY ./pom.xml /home/multiverse

RUN mvn -f /home/multiverse/pom.xml -pl configuration-microservice -am clean install -Dmaven.test.skip=true


# Package stage
FROM java:8-jre

COPY --from=build /home/multiverse/configuration-microservice/target/configuration-microservice-fat.jar /opt/verticles/configuration-microservice.jar
COPY --from=build /home/multiverse/configuration-microservice/src/config/docker.json /opt/verticles/

EXPOSE 8088

WORKDIR /opt/verticles

ENTRYPOINT ["sh", "-c"]
CMD ["java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar configuration-microservice.jar -cluster -ha -conf docker.json"]