FROM registry.cn-hangzhou.aliyuncs.com/jerry-up/java:8

ARG POM_VERSION

ENV POM_VERSION $POM_VERSION

ENV JVM_OPTIONS=""

COPY boot boot

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

ENTRYPOINT exec java -jar $JVM_OPTIONS boot/boot-$POM_VERSION.jar