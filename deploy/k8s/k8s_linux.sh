#!/bin/bash
source ./k8s.properties

if [ ! -d $DEPLOY_DIR ]; then
    mkdir -p $DEPLOY_DIR
fi

WORK_DIR=$(pwd)

cd ../
sh ./compile.sh $WORK_DIR

cd $WORK_DIR

IMAGE_NAME=registry.cn-hangzhou.aliyuncs.com/jerry-up/boot:$POM_VERSION-RC

docker build -t $IMAGE_NAME --build-arg POM_VERSION=$POM_VERSION .

docker push $IMAGE_NAME

rm -rf $DEPLOY_DIR/start.sh $DEPLOY_DIR/stop.sh $DEPLOY_DIR/k8s.properties $DEPLOY_DIR/boot.yaml

cp ./start.sh ./stop.sh ./k8s.properties ./boot.yaml $DEPLOY_DIR/

sed -i "s/{{NACOS_CONFIG_HOST}}/$NACOS_CONFIG_HOST/g" $DEPLOY_DIR/boot.yaml
sed -i "s/{{NACOS_CONFIG_ENV}}/$NACOS_CONFIG_ENV/g" $DEPLOY_DIR/boot.yaml
sed -i "s/{{POM_VERSION}}/$POM_VERSION/g" $DEPLOY_DIR/boot.yaml

DEPLOY_DIR_ESCAPE=$(echo "$DEPLOY_DIR" | sed -s "s/\//\\\\\//g")
sed -i "s/{{DEPLOY_DIR}}/$DEPLOY_DIR_ESCAPE/g" $DEPLOY_DIR/boot.yaml

SERVER_PORT=`sh ./server_port.sh`

sed -i "s/{{SERVER_PORT}}/$SERVER_PORT/g" $DEPLOY_DIR/boot.yaml

rm -rf $WORK_DIR/boot
