#!/bin/bash
source ./k8s.properties

if [[ `kubectl get configmap|grep boot-env` == *boot-env* ]];then
    kubectl delete configmap boot-env
fi

if [[ `kubectl get deployment|grep boot-$POM_VERSION` == *boot-$POM_VERSION* ]];then
    kubectl delete deployment boot-$POM_VERSION
fi

if [[ `kubectl get service|grep boot-service` == *boot-service* ]];then
    kubectl delete service boot-service
fi