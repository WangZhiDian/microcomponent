#!/bin/bash

SERVICE_JAR_FILE_NAME=price-control-1.0.0-SNAPSHOT.jar
SERVICE_INSTALL_DIR=/opt/cloud/price_control
SERVICE_JAR_PATH=${SERVICE_INSTALL_DIR}/${SERVICE_JAR_FILE_NAME}

echo "Shutting down service..."
process_id=`ps -ef|grep -i "${SERVICE_JAR_PATH}" |grep -v "grep"|awk '{print $2}'`
if [ -z "${process_id}" ];then
    echo "There is no service running!"
else
    kill -9 ${process_id}
    echo "Service " ${process_id} " is killed!"
fi
echo "Service stop success!"
