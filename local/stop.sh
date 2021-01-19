#!/usr/bin/env bash

ps -ef | grep java | tr -s ' ' | cut -d " " -f3 | \
while read pid; 
do 
    echo killing: $pid;
    kill -9 $pid
done

docker-compose -f docker-compose.yml stop