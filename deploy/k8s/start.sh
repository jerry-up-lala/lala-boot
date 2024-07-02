#!/bin/bash
source ./k8s.properties

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

if [[ `kubectl get configmap|grep boot-env` == *boot-env* ]];then
    kubectl delete configmap boot-env
fi

if [[ `kubectl get deployment|grep boot-$POM_VERSION` == *boot-$POM_VERSION* ]];then
    kubectl delete deployment boot-$POM_VERSION
fi

if [[ `kubectl get service|grep boot-service` == *boot-service* ]];then
    kubectl delete service boot-service
fi

kubectl apply -f $DEPLOY_DIR/boot.yaml

echo "部署目录: $DEPLOY_DIR 数据文件目录：$DATA_PATH Nacos配置目录：$NACOS_PATH"