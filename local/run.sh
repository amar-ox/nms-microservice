#!/usr/bin/env bash

ps -ef | grep java | tr -s ' ' | cut -d " " -f3 | \
while read pid; 
do 
    echo killing: $pid;
    kill -9 $pid
done
sleep 5

# Stop
docker-compose -f docker-compose.yml stop

# Start persistence containers only
docker-compose -f docker-compose.yml up -d mysql mongo activemq
echo "Waiting for persistence init..."
sleep 20

mvn -f ../pom.xml clean install

# account
java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar ../account-microservice/target/account-microservice-fat.jar -cluster -ha -conf ../account-microservice/src/config/local.json > multiverse.log 2>&1 &
sleep 20
# telemetry
java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar ../telemetry-microservice/target/telemetry-microservice-fat.jar -cluster -ha -conf ../telemetry-microservice/src/config/local.json > multiverse.log 2>&1 &
sleep 20
# notification
java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar ../notification-microservice/target/notification-microservice-fat.jar -cluster -ha -conf ../notification-microservice/src/config/local.json > multiverse.log 2>&1 &
sleep 20
# topology
java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar ../topology-microservice/target/topology-microservice-fat.jar -cluster -ha -conf ../topology-microservice/src/config/local.json &
sleep 20
# configuration
java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar ../configuration-microservice/target/configuration-microservice-fat.jar -cluster -ha -conf ../configuration-microservice/src/config/local.json &
sleep 20
# api-gw
java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar ../api-gateway/target/api-gateway-fat.jar -cluster -ha -conf ../api-gateway/src/config/local.json > multiverse.log 2>&1 &