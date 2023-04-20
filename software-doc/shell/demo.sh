#!/bin/bash

current_work_dir=$(cd `dirname $0`; pwd)
service_file_name=price-control-1.0.0-SNAPSHOT.tar.gz
# 需要部署的服务器ip
service_ip=$1

if [ $# -eq 0 ]; then
    echo "need the server ip"
    exit
fi
# 检查是否有安装包
if [ ! -f ${current_work_dir}/${service_file_name} ];then
  exit
fi
# 1 拷贝安装包到目标服务器（默认该安装包和本脚本在同一个目录）
scp ${current_work_dir}/${service_file_name} root@${service_ip}:/root

# 2 解压目标服务器安装包
ssh root@${service_ip} "tar -xzvf ${service_file_name}"

# 3 执行目标服务器解压后目录bin中的编排脚本停止服务
ssh root@${service_ip} "sh /root/price-control/bin/playbook.sh"

