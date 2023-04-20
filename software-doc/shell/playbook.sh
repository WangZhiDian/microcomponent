#!/bin/bash

#安装包为/root/price-control-1.0.0-SNAPSHOT.tar.gz
#解压目录为/root/price_control
current_work_dir=$(cd `dirname $0`; pwd)
script_dir=${current_work_dir}/
backup_dir=/opt/application/

# 检查是否有安装包
if [ ! -d ${current_work_dir}/../../price-control ];then
  exit
fi

#编排过程
# 1 执行目标服务器解压后目录bin中的stop脚本停止服务
sh ${current_work_dir}/stop.sh
# 2 执行目标服务器解压后目录bin中的install --uninstall脚本卸载服务
sh ${current_work_dir}/install.sh --uninstall
# 3 执行目标服务器解压后目录bin中的install --install脚本安装服务
sh ${current_work_dir}/install.sh --install
# 4 执行目标服务器解压后目录bin中的start脚本卸载服务
sh ${current_work_dir}/start.sh
# 5 拷贝安装包到服务器备份目录
if [ ! -d ${backup_dir} ];then
  mkdir -p ${backup_dir}
fi

backup_file="price-control-1.0.0-SNAPSHOT"_`date +%Y%m%d%H%M%S`_"tar.gz"
mv /root/price-control-1.0.0-SNAPSHOT.tar.gz ${backup_dir}/${backup_file}
