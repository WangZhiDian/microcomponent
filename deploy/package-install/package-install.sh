#!/bin/bash

current_work_dir=$(cd `dirname $0`; pwd)
service_file_name=price-control-1.0.0-SNAPSHOT.tar.gz

html_file_name=price_control_ui.tar.gz
html_file_dir=/opt/cloud/price_html
html_backup_dir=/opt/application/price_ui

# service_type 为部署java service还是nginx html，值：service，html
service_type=$1
# 需要部署的服务器ip，该文件需要和安装包放在同一级目录下
service_ip=$2
# env_type 为部署java 服务环境，值：dev prod
env_type=$3

function usage()
{
    echo "  Usage: remote install.sh [help]"
    echo ""
    echo "  install service or html. service type:dev prod"
    echo ""
    echo "  help                                : help."
    echo ""
    echo "  order like: param ip type env_type  : service 172.16.40.62 "
    echo "  order -> sh install.sh service 172.16.40.62 dev"
    echo "  order -> sh install.sh service 172.16.40.62 prod"
    echo "  order -> sh install.sh html 172.16.40.62"

}

function deploy_service()
{
    echo "deploy service start -----"
    # 检查是否有安装包
    if [ ! -f ${current_work_dir}/${service_file_name} ];then
      exit;
    fi
    # 1 拷贝安装包到目标服务器（默认该安装包和本脚本在同一个目录）
    scp ${current_work_dir}/${service_file_name} root@${service_ip}:/root

    # 2 解压目标服务器安装包
    ssh root@${service_ip} "tar -xzvf ${service_file_name}"

    # 3 执行目标服务器解压后目录bin中的编排脚本停止服务
    ssh root@${service_ip} "sh /root/price-control/bin/playbook.sh ${env_type}"

    echo "deploy service end -----"
}

function deploy_html()
{
    echo "deploy html start -----"
    # 1 校验包本地安装包是否存在
    if [ ! -f ${current_work_dir}/${html_file_name} ];then
      echo "there is no html tar file ---"
      exit;
    fi
    # 2 停止nginx
    ssh root@${service_ip} "/usr/local/nginx/sbin/nginx -s stop"
    # 3 拷贝本地安装包到远程服务器
    scp ${current_work_dir}/${html_file_name} root@${service_ip}:/root
    # 4 解压远程服务器安装包到安装目录,目录提前创建好
    ssh root@${service_ip} "tar -xzvf ${html_file_name} -C ${html_file_dir}"
    ssh root@${service_ip} "chmod -R 755 ${html_file_dir}"
    # 5 启动nginx
    ssh root@${service_ip} "/usr/local/nginx/sbin/nginx"
    # 6 将远程服务器上的html的安装包版本备份
    if [ ! -d ${html_backup_dir} ];then
      mkdir -p ${html_backup_dir}
    fi
    backup_file="price_control_ui"_`date +%Y%m%d%H%M%S`_"tar.gz"
    ssh root@${service_ip} "mv /root/price_control_ui.tar.gz ${html_backup_dir}/${backup_file}"

    echo "deploy html end -----"
}


if [ "${service_type}" == "html" ]; then
    if [ ! $# -eq 2 ]; then
      echo "  error info: need the server ip and service type."
      usage
      exit
    fi
    deploy_html
elif [ "${service_type}" == "service" ]; then
    if [ ! $# -eq 3 ]; then
      echo "  error info: need the server ip and service type env type."
      usage
      exit
    fi
    env_type=$3
    deploy_service ${env_type}
elif [ "${service_type}" == "help" ]; then
    usage
else
    echo "Unknown argument"
fi
