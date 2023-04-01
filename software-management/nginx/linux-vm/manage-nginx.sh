#!/bin/bash

CURRENT_WORK_DIR=$(cd `dirname $0`; pwd)

app_install_path=/usr/local/nginx
user_group=nginxgroup
user_info=nginx
nginx_name=nginx-1.23.3

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

    #安装依赖包
    yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel

    #解压nginx代码包
    tar -xzvf ${CURRENT_WORK_DIR}/${nginx_name}.tar.gz
    cd ${nginx_name}
    ./configure --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module

    make & make install

    rm -f ${app_install_path}/conf/nginx.conf
    cp ${CURRENT_WORK_DIR}/nginx.conf ${app_install_path}/conf/

    # linux创建软连接命令： ln -s [dir1] [dir2]  ，dir1是真实的文件夹，dir2是dir1的软链接
    ln -s ${app_install_path}/sbin/nginx /usr/sbin/nginx

    echo "install app nginx end."
}

function uninstall()
{
    if [ -f '/usr/bin/nginx' ]; then
          rm -f /usr/bin/nginx
    fi

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
