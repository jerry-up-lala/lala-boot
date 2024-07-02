#!/bin/bash
source ./jar.properties

if [ ! -d $DEPLOY_DIR ]; then
    mkdir -p $DEPLOY_DIR
fi

WORK_DIR=$(pwd)

cd ../
sh ./compile.sh $DEPLOY_DIR

cd $WORK_DIR

rm -rf $DEPLOY_DIR/start.sh $DEPLOY_DIR/stop.sh $DEPLOY_DIR/jar.properties

cp ./start.sh ./stop.sh ./jar.properties $DEPLOY_DIR/