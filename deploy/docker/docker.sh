#!/bin/bash
source ./docker.properties

if [ ! -d $DEPLOY_DIR ]; then
    mkdir -p $DEPLOY_DIR
fi

WORK_DIR=$(pwd)

cd ../
sh ./compile.sh $WORK_DIR

cd $WORK_DIR

IMAGE_NAME=$DOCKER_REGISTRY/boot:$POM_VERSION-RC

docker build -t $IMAGE_NAME --build-arg POM_VERSION=$POM_VERSION .

docker push $IMAGE_NAME

rm -rf $DEPLOY_DIR/start.sh $DEPLOY_DIR/stop.sh $DEPLOY_DIR/server_port.sh $DEPLOY_DIR/docker.properties

cp ./start.sh ./stop.sh ./server_port.sh ./docker.properties $DEPLOY_DIR/

rm -rf $WORK_DIR/boot
