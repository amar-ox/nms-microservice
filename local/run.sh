#!/usr/bin/env bash

# Stop
docker-compose -f docker-compose.yml stop

# Start persistence containers only
docker-compose -f docker-compose.yml up -d mysql mongo activemq
echo "Waiting for persistence init..."
sleep 30

# mvn -f ../pom.xml clean install

# notification
java -jar ../notification-microservice/target/notification-microservice-fat.jar -cluster -ha -conf ../notification-microservice/src/config/local.json &
sleep 10
# topology
java -jar ../topology-microservice/target/topology-microservice-fat.jar -cluster -ha -conf ../topology-microservice/src/config/local.json &
sleep 10
# account
java -jar ../account-microservice/target/account-microservice-fat.jar -cluster -ha -conf ../account-microservice/src/config/local.json &
sleep 10
# telemetry
java -jar ../telemetry-microservice/target/telemetry-microservice-fat.jar -cluster -ha -conf ../telemetry-microservice/src/config/local.json &
sleep 10
# configuration
java -jar ../configuration-microservice/target/configuration-microservice-fat.jar -cluster -ha -conf ../configuration-microservice/src/config/local.json &
sleep 10
# api-gw
java -jar ../api-gateway/target/api-gateway-fat.jar -cluster -ha -conf ../api-gateway/src/config/local.json &