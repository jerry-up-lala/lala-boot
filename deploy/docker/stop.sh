#!/bin/bash
source ./docker.properties

CONTAINER_NAME=boot-${POM_VERSION}

CONTAINER_ID=`docker ps -aq --filter name=$CONTAINER_NAME`
if [ -z "$CONTAINER_ID" ]; then
  echo "$CONTAINER_NAME 未运行"
else
  docker stop $CONTAINER_ID
  docker rm $CONTAINER_ID
fi