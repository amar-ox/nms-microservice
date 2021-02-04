# Build stage
FROM maven:3.6.3-jdk-11 as build

COPY ./ /home/multiverse
COPY ./pom.xml /home/multiverse

RUN mvn -f /home/multiverse/pom.xml -pl api-gateway -am clean install -Dmaven.test.skip=true


# Package stage
FROM openjdk:11.0.4-jre-slim

COPY --from=build /home/multiverse/api-gateway/target/api-gateway-fat.jar /opt/verticles/api-gateway.jar
COPY --from=build /home/multiverse/api-gateway/src/config/docker.json /opt/verticles/

EXPOSE 8787

WORKDIR /opt/verticles

ENTRYPOINT ["sh", "-c"]
CMD ["java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar api-gateway.jar -cluster -ha -conf docker.json"]