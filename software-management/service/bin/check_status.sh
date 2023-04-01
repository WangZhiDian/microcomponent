#!/bin/bash

SERVICE_JAR_FILE_NAME=price-control-1.0.0-SNAPSHOT.jar
SERVICE_INSTALL_DIR=/opt/cloud/price_control
SERVICE_JAR_NAME={{SERVICE_INSTALL_DIR}}/${SERVICE_JAR_FILE_NAME}

process_id=`ps -ef|grep -i "${SERVICE_JAR_NAME}" |grep -v "grep"|awk '{print $2}'`
if [ -z "${process_id}" ];then
    echo "Service is not running!"
    exit 1
else
    echo "Service is running!"
    exit 0
fi
