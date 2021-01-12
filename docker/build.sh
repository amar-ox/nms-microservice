#!/usr/bin/env bash

set -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

docker build -t "api-gateway" $DIR/../api-gateway
docker build -t "telemetry-microservice" $DIR/../telemetry-microservice
docker build -t "topology-microservice" $DIR/../topology-microservice
docker build -t "notification-microservice" $DIR/../notification-microservice
docker build -t "configuration-microservice" $DIR/../configuration-microservice
docker build -t "account-microservice" $DIR/../account-microservice