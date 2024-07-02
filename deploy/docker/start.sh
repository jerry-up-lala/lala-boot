#!/bin/bash
source ./docker.properties

DATA_PATH=$DEPLOY_DIR/data

if [ ! -d $DATA_PATH ]; then
    mkdir -p $DATA_PATH
fi

NACOS_PATH=$DEPLOY_DIR/nacos

if [ ! -d $NACOS_PATH ]; then
    mkdir -p $NACOS_PATH
fi

LOGS_DIR=$DATA_PATH/logs

if [ ! -d $LOGS_DIR ]; then
    mkdir -p $LOGS_DIR
fi

IMAGE_NAME=$DOCKER_REGISTRY/boot:$POM_VERSION-RC
docker pull $IMAGE_NAME

CONTAINER_NAME=boot-${POM_VERSION}

SERVER_PORT=`sh $DEPLOY_DIR/server_port.sh`

docker run -d --pull="always" --privileged="true" \
-e NACOS_CONFIG_HOST=$NACOS_CONFIG_HOST \
-e NACOS_CONFIG_ENV=$NACOS_CONFIG_ENV \
-e JVM_OPTIONS="-Xmx1024m \
    -Xms1024m \
    -Xmn512m \
    -Xss256k \
    -XX:SurvivorRatio=8 \
    -XX:MetaspaceSize=128m \
    -XX:MaxMetaspaceSize=384m \
    -XX:-OmitStackTraceInFastThrow \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/data/logs/heapdump.hprof \
    -XX:+UseCMSInitiatingOccupancyOnly \
    -XX:CMSInitiatingOccupancyFraction=75 \
    -Xloggc:/data/logs/loggc.log \
    -XX:+PrintGCDetails \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCApplicationConcurrentTime \
    -XX:+PrintHeapAtGC \
    -XX:+UseGCLogFileRotation \
    -XX:NumberOfGCLogFiles=5 \
    -XX:GCLogFileSize=5M" \
-p $SERVER_PORT:8080 \
-v $DATA_PATH:/data \
-v $NACOS_PATH:/root/nacos \
--name $CONTAINER_NAME \
-d $IMAGE_NAME

echo "部署目录: $DEPLOY_DIR 数据文件目录：$DATA_PATH Nacos配置目录：$NACOS_PATH 服务端口: $SERVER_PORT"