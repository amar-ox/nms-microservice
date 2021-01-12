# Build stage
FROM maven:3.6.3-openjdk-8 as build

COPY ./ /home/multiverse
COPY ./pom.xml /home/multiverse

RUN mvn -f /home/multiverse/pom.xml -pl account-microservice -am clean install -Dmaven.test.skip=true


# Package stage
FROM java:8-jre

COPY --from=build /home/multiverse/account-microservice/target/account-microservice-fat.jar /opt/verticles/account-microservice.jar
COPY --from=build /home/multiverse/account-microservice/src/config/docker.json /opt/verticles/

EXPOSE 8083

WORKDIR /opt/verticles

ENTRYPOINT ["sh", "-c"]
CMD ["java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar account-microservice.jar -cluster -ha -conf docker.json"]