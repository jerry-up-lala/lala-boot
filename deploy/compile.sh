#!/bin/bash
COMPILE_DIR=$1

if [ $# -eq 0 ]; then
    echo "请输入编译输出目录"
    exit 1
fi

if [ ! -d $COMPILE_DIR ]; then
    mkdir -p $COMPILE_DIR
fi

WORK_DIR=$(pwd)

cd $WORK_DIR/../boot
mvn clean install -Dmaven.test.skip=true
rm -rf $COMPILE_DIR/boot
tar -xf ./target/zip/boot.tar.gz -C $COMPILE_DIR

cd $WORK_DIR