#!/usr/bin/env bash

set -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

docker build -t "nms/api-gateway" $DIR/../api-gateway
#docker build -t "nms/cache-infrastructure" $DIR/../cache-infrastructure
docker build -t "nms/telemetry-microservice" $DIR/../telemetry-microservice
docker build -t "nms/topology-microservice" $DIR/../topology-microservice
docker build -t "nms/notification-microservice" $DIR/../notification-microservice
docker build -t "nms/configuration-microservice" $DIR/../configuration-microservice
docker build -t "nms/account-microservice" $DIR/../account-microservice