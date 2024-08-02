#!/bin/bash
source ./jar.properties

DATA_PATH=$DEPLOY_DIR/data

if [ ! -d $DATA_PATH ]; then
    mkdir -p $DATA_PATH
fi

LOGS_DIR=$DATA_PATH/logs

if [ ! -d $LOGS_DIR ]; then
    mkdir -p $LOGS_DIR
fi

JAR_PATH=$DEPLOY_DIR/boot/boot-$POM_VERSION.jar

nohup java -jar \
    -Xmx1024m \
    -Xms1024m \
    -Xmn512m \
    -Xss256k \
    -XX:SurvivorRatio=8 \
    -XX:MetaspaceSize=128m \
    -XX:MaxMetaspaceSize=384m \
    -XX:-OmitStackTraceInFastThrow \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=$LOGS_DIR/heapdump.hprof \
    -XX:+UseCMSInitiatingOccupancyOnly \
    -XX:CMSInitiatingOccupancyFraction=75 \
    -Xloggc:$LOGS_DIR/gc.log \
    -XX:+PrintGCDetails \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCApplicationConcurrentTime \
    -XX:+PrintHeapAtGC \
    -XX:+UseGCLogFileRotation \
    -XX:NumberOfGCLogFiles=5 \
    -XX:GCLogFileSize=5M \
    $JAR_PATH \
    --DATA_PATH=$DATA_PATH \
    --NACOS_CONFIG_HOST=$NACOS_CONFIG_HOST \
    --NACOS_CONFIG_GROUP=$NACOS_CONFIG_GROUP \
    --NACOS_CONFIG_ENV=$NACOS_CONFIG_ENV \
    > $LOGS_DIR/boot.log 2>&1 &

echo "部署目录: $DEPLOY_DIR 日志文件目录：$LOGS_DIR "