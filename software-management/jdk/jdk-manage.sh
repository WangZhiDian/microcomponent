#!/bin/bash

CURRENT_WORK_DIR=$(cd `dirname $0`; pwd)
source ${CURRENT_WORK_DIR}/config.properties

jdk_install_path=${install_path}
rar_file=${CURRENT_WORK_DIR}/${jdk_rar_name}

function usage()
{
    echo "Usage: install.sh [--help]"
    echo ""
    echo "install jdk."
    echo ""
    echo "  --help                  : help."
    echo ""
    echo "  --install               : install."
    echo "  --uninstall             : uninstall."
}

function install()
{
    if [ -d ${jdk_install_path} ]; then
      echo "jdk already installed,please uninstall first."
    else
      #没安装过jdk才开始安装
      # 安装可能需要的插件
      yum -y install glibc.i686
      #先在opt中创建一个安装文件夹
      mkdir -p ${jdk_install_path}
      #将jdk解压到opt中
      tar -xzf ${rar_file} -C ${jdk_install_path} --strip-components 1
      #将解压好的jdk文件移动到opt/soft中，并改名为jdk180(解压后文件名叫jdk1.8.0_111)
      # tar -xzvf jdk-8u251-linux-i586.tar.gz -C /opt/sortware/jdk/test_jdk/ --strip-components 1
      #mv /opt/jdk1.8.0_111 /opt/soft/jdk180

      #配置环境变量
      sed -i '/JAVA_HOME/d' /etc/profile
      echo '# JAVA_HOME Environment' >> /etc/profile
      echo "export JAVA_HOME=${jdk_install_path}" >> /etc/profile
      echo 'export PATH=$PATH:${JAVA_HOME}/bin' >> /etc/profile
      echo 'export CLASSPATH=.:${JAVA_HOME}/jre/lib/rt.jar:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar' >> /etc/profile

      #使配置环境生效
      source /etc/profile
      echo '====================jdk complete==============='
    fi

    return 0
}

function uninstall()
{
    #制定固定的目录，别被乱删了
    fix_path="/opt/3rd"
    if [ -d ${jdk_install_path} ]; then
      if [[ ${jdk_install_path} = ${fix_path}* ]]
      then
          rm -rf ${jdk_install_path}
          sed -i '/JAVA_HOME/d' /etc/profile
          echo "Uninstall success."
      else
          echo "请确认目录"
      fi
    else
      echo "没有对应的安装目录，未安装该版本包，请提前确认。"
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
