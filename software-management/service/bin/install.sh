#!/bin/bash

CURRENT_WORK_DIR=$(cd `dirname $0`; pwd)

app_install_path=/opt/cloud/price_control
log_path=/opt/cloud/logs
user_group=servicegroup
user_info=service

function usage()
{
    echo "Usage: install.sh [--help]"
    echo ""
    echo "install service."
    echo ""
    echo "  --help                  : help."
    echo ""
    echo "  --install               : install."
    echo "  --uninstall             : uninstall."
}

function check_user_group()
{
    local tmp=$(cat /etc/group | grep ${1}: | grep -v grep)

    if [ -z "$tmp" ]; then
        return 2
    else
        return 0
    fi
}

function check_user()
{
   if id -u ${1} >/dev/null 2>&1; then
        return 0
    else
        return 2
    fi
}

function install()
{
    check_user_group ${user_group}
    if [[ "$?" -eq "2" ]]; then
      groupadd ${user_group}
      echo "add user group: ${user_group}"
    fi

    check_user ${user_info}
    if [[ "$?" -eq "2" ]]; then
      useradd -g ${user_group} ${user_info}
      echo "add user : ${user_info}"
    fi

    if [ ! -d ${app_install_path} ];then
      mkdir -p ${app_install_path}
    fi

    if [ ! -d ${log_path} ];then
      mkdir -p ${log_path}
      chown -R ${user_info}:${user_group} ${log_path}
      chmod -R 750 ${log_path}
    fi

    cp -R ${CURRENT_WORK_DIR}/../lib ${app_install_path}
    cp -R ${CURRENT_WORK_DIR}/../lib/price-control-1.0.0-SNAPSHOT.jar ${app_install_path}
    rm -f ${CURRENT_WORK_DIR}/../conf/*.formatted
    cp -R ${CURRENT_WORK_DIR}/../conf ${app_install_path}
    rm -f ${CURRENT_WORK_DIR}/../bin/*.formatted
    cp -R ${CURRENT_WORK_DIR}/../bin ${app_install_path}

    chown -R ${user_info}:${user_group} ${app_install_path}
    chmod -R 777 ${app_install_path}/bin/*.sh
    chmod -R 750 ${app_install_path}/conf
    chmod -R 750 ${app_install_path}/lib

    echo "install app price_control end."
}

function uninstall()
{
    #制定固定的目录，别被乱删了
    if [ -d ${app_install_path} ]; then
      rm -rf ${app_install_path}
      echo "uninstall end."
    else
      echo "no install directory"
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
else
    echo "Unknown argument"
fi
