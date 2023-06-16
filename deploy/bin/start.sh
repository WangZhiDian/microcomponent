#!/bin/bash

SERVICE_JAR_FILE_NAME=price-control-1.0.0-SNAPSHOT.jar
SERVICE_INSTALL_DIR=/opt/cloud/price_control
SERVICE_SERVER_XMX=2g
SERVICE_JAR_PATH=${SERVICE_INSTALL_DIR}/${SERVICE_JAR_FILE_NAME}
SERVICE_LIB_PATH=${SERVICE_INSTALL_DIR}/lib
SERVICE_CONF_PATH=${SERVICE_INSTALL_DIR}/conf
SERVICE_JDK_LIB=/opt/3rd/jdk/jre/lib/ext/

CURRENT_DIR=`pwd`

process_id=`ps -ef|grep -i "${SERVICE_JAR_PATH}" |grep -v "grep"|awk '{print $2}'`
if [ -z "${process_id}" ];then
    echo "Starting service..."
    cd ${SERVICE_INSTALL_PATH}
    source /etc/profile
    su service -c "nohup java -jar -Xmx${SERVICE_SERVER_XMX} -Djava.ext.dirs=${SERVICE_JDK_LIB}:${SERVICE_LIB_PATH} -Dspring.config.location=${SERVICE_CONF_PATH}/ -Dconfig.dir=${SERVICE_CONF_PATH}/ -Dlogging.config=${SERVICE_CONF_PATH}/logback.xml ${SERVICE_JAR_PATH} 1>/dev/null 2>/dev/null &"
    cd ${CURRENT_DIR} 
    sleep 5
    process_id=`ps -ef|grep -i "${SERVICE_JAR_PATH}" |grep -v "grep"|awk '{print $2}'`
    if [ -z "${process_id}" ];then
        echo "Start service error!"
        exit 1
    else
        echo "Start service success!"
    fi
else
    echo "Service is running!"
fi
