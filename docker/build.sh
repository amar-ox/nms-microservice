#!/usr/bin/env bash

set -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

docker build -t "nms/api-gateway" $DIR/../api-gateway
#docker build -t "nms/cache-infrastructure" $DIR/../cache-infrastructure
docker build -t "nms/data-streaming-microservice" $DIR/../data-streaming-microservice
docker build -t "nms/topology-microservice" $DIR/../topology-microservice