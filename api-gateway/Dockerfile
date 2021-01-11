FROM java:8-jre

ENV VERTICLE_FILE target/api-gateway-fat.jar

# Set the location of the verticles
ENV VERTICLE_HOME /opt/verticles
ENV SSL /opt/ssl

EXPOSE 8787

COPY $VERTICLE_FILE $VERTICLE_HOME/

COPY src/config/docker.json $VERTICLE_HOME/

COPY src/main/resources/cert/*.pem $SSL/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar api-gateway-fat.jar -cluster -ha -conf docker.json"]
