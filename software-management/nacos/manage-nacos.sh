#!/bin/bash

CURRENT_WORK_DIR=$(cd `dirname $0`; pwd)
source ${CURRENT_WORK_DIR}/nacos.properties

function usage()
{
    echo "Usage: install.sh [--help]"
    echo ""
    echo "manage nacos, install in /usr/local/nacos."
    echo ""
    echo "  --help                  : help."
    echo ""
    echo "  --install               : install."
    echo "  --uninstall             : uninstall."
    echo "  --start                 : start."
    echo "  --stop                  : stop."
    echo "  --status                : status."
}

function check_dir()
{
    if [ -d ${1} ]; then
        return 0
    else
        return 2
    fi
}

function check_exist()
{
    check_dir '/usr/local/nacos'
    if [ $? -eq 2 ]; then
        echo "nacos is not exist."
        exit 0;
    fi
}

function check_port()
{
    echo "正在检测端口......"
    port_exist=`netstat -tlpn | grep "\b$1\b"`
    if [ "$port_exist"x == ''x ]; then
        return 1;
    else
        return 0;
    fi
}

function install()
{
    check_dir '/usr/local/nacos'
    if [ $? -eq 1 ]; then
        echo "nacos is exist. please uninstall first."
        exit 0;
    fi

    if [ ! -f ${CURRENT_WORK_DIR}/${NACOS_PKG} ]; then
        echo "nacos pkg ${NACOS_PKG} is not exist"
        exit 0;
    fi

    tar -xzvf ${CURRENT_WORK_DIR}/${NACOS_PKG}

    if [ ${NACOS_CONSTANCY} == "y" ]; then
        echo "spring.datasource.platform="${DB_PLATFORM} >> ${CURRENT_WORK_DIR}/nacos/conf/application.properties
        echo "db.num="${DB_NUM} >> ${CURRENT_WORK_DIR}/nacos/conf/application.properties
        echo "db.url.0="${DB_URL} >> ${CURRENT_WORK_DIR}/nacos/conf/application.properties
        echo "db.user="${DB_USER} >> ${CURRENT_WORK_DIR}/nacos/conf/application.properties
        echo "db.password="${DB_PASSWORD} >> ${CURRENT_WORK_DIR}/nacos/conf/application.properties
    fi
    if [ ${NACOS_TYPE} == "cluster" ]; then
        echo "not deal cluster yet.."
    fi

    mv ${CURRENT_WORK_DIR}/nacos /usr/local/

    echo "Install success."

    nacos_start

    return 0
}

function uninstall()
{
    check_exist

    nacos_stop
    rm -rf '/usr/local/nacos'

    echo "Uninstall success."
}

function nacos_stop()
{
    check_exist
    sh /usr/local/nacos/bin/shutdown.sh
    sleep 1s
    check_port 8848
    if [ $? -eq 0 ]; then
        echo "shutdown nacos error."
        exit 1;
    else
        echo "shutdown nacos success"
    fi
}

function nacos_start()
{
    check_exist
    check_port 8848
    if [ $? -eq 0 ]; then
        echo "nacos is already running."
        exit 0;
    fi

    if [ ${NACOS_TYPE} == "single" ]; then
        sh /usr/local/nacos/bin/startup.sh -m standalone
    fi
    if [ ${NACOS_TYPE} == "cluster" ]; then
        sh /usr/local/nacos/bin/startup.sh
    fi
    echo "nacos is starting nacos ,wait seconds!"
}

function nacos_status()
{
    check_exist
    check_port 8848
    if [ $? -eq 0 ]; then
        echo "nacos is running !"
        exit 1;
    else
        echo "nacos is not running !"
    fi
}

if [ ! `id -u` = "0" ]; then
    echo "Please run as root user"
    exit 1
fi

if [ $# -eq 0 ]; then
    usage
    exit
fi

opt=$1

if [ "${opt}" == "--install" ]; then
    install
elif [ "${opt}" == "--uninstall" ]; then
    uninstall
elif [ "${opt}" == "--help" ]; then
    usage
elif [ "${opt}" == "--start" ]; then
    nacos_start
elif [ "${opt}" == "--stop" ]; then
    nacos_stop
elif [ "${opt}" == "--status" ]; then
    nacos_status
else
    echo "Unknown argument"
fi