#!/bin/bash
source ./jar.properties

JAR_PATH=$DEPLOY_DIR/boot/boot-$POM_VERSION.jar

PID=`ps -ef | grep $JAR_PATH | grep -v grep`
if [ -z "$PID" ]; then
  echo "lala-boot未运行"
else
  ps aux | grep $JAR_PATH | grep -v grep | awk '{print $2}' | xargs kill -9
fi