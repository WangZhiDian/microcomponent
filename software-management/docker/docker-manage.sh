#!/bin/bash

function usage()
{
    echo "Usage: install.sh [--help]"
    echo ""
    echo "manage docker use net-work."
    echo ""
    echo "  --help                  : help."
    echo "  ------install-----"
    echo "  --install               : install."
    echo "  --uninstall             : 卸载docker."
    echo "  --uninstall-all         : 卸载docker并将所有镜像和容器文件删掉."
    echo "  ------manage------"
    echo "  --status                : status."
    echo "  --start                 : start."
    echo "  --stop                  : stop."
}

function install()
{
    docker_status
    if [ $? -eq 0 ]; then
        echo "docker is already running."
        return 1
    fi
    # 安装yum-utils， 其中包含了yum-config-manager等工具
    yum install -y yum-utils
    yum-config-manager \
        --add-repo \
        https://download.docker.com/linux/centos/docker-ce.repo

    #默认安装等最新版本等docker
    if [ $# -eq 0 ]; then
        yum -y install docker-ce docker-ce-cli containerd.io docker-compose-plugin
    else
        docker_version_exist=`yum list docker-ce-$1|grep $1| gawk '{ print $2}'`
        if [[ $docker_version_exist =~ $1 ]] ; then
            echo "install docker with version: docker-ce-"$1
            yum -y install docker-ce-$1 docker-ce-cli-$1 containerd.io docker-compose-plugin
        else
            echo "have no docker version" $1
            exit 0;
        fi
    fi
    echo "Install success."

    # 设置docker镜像源为ali的镜像源
    if [ ! -d '/etc/docker' ]; then
        mkdir -p /etc/docker
    fi
    if [ -f '/etc/docker/daemon.json' ]; then
        rm -f 'etc/docker/daemon.json'
    fi
    echo "{" > /etc/docker/daemon.json
    echo '    registry-mirrors": ["https://joj9v4bz.mirror.aliyuncs.com"]' > /etc/docker/daemon.json
    echo "}" > /etc/docker/daemon.json

    systemctl start docker

    return 0
}

function uninstall()
{
    # preparation 1.杀死docker有关的容器
    #docker kill $(docker ps -a -q)

    # preparation 2.删除所有docker容器：
    #docker rm $(docker ps -a -q)

    # preparation 3.删除所有docker镜像：
    #docker rmi $(docker images -q)

    # preparation 4.停止 docker 服务：
    docker_status
    if [ $? != 0 ]; then
        exit 0;
    fi
    systemctl stop docker
    systemctl stop docker.socket

    # preparation 5.卸载docker软件：
    #result=`yum list installed | grep docker`

    #for i in ${result[@]}
    #do
    #    echo "Remove package "$i
        #yum remove -y $i
    #done

    yum -y remove docker-*
    yum -y remove containerd.io*

    # 如果存在卸载旧版本的docker
    yum remove docker \
              docker-client \
              docker-client-latest \
              docker-common \
              docker-latest \
              docker-latest-logrotate \
              docker-logrotate \
              docker-engine

    echo "Uninstall docker service success."
}

function uninstall_all()
{

    uninstall

    # preparation 6.删除存储目录 镜像和容器
    if [ -d  '/etc/docker' ]; then
        rm -rf /etc/docker
    fi
    if [ -d  '/run/docker' ]; then
        rm -rf /run/docker
    fi
    if [ -d  ' /var/lib/docker' ]; then
        rm -rf  /var/lib/docker
    fi
    if [ -d  '/var/lib/containerdr' ]; then
        #rm -rf /var/lib/containerd
        echo "rm /var/lib/containerd"
    fi

    echo "Uninstall success."
}

function start()
{
    docker_status
    if [ $? -eq 1 ]; then
        echo "docker start error"
        exit 0;
    fi
    systemctl start docker

    # 如果服务器重启后，docker没有自动启动，可能需要手动启动一下docker.socket
    # systemctl start docker.socket
}

function stop()
{
    docker_status
    if [ $? -eq 1 ]; then
        echo "docker stop error"
        exit 0;
    fi
    systemctl stop docker
}

function docker_status()
{

    if [ ! -f '/usr/bin/docker' ]; then
        echo "docker not install"
        return 1;
    fi

    docker_status_running="running"
    docker_cur_status=`systemctl status docker | grep '(running)'`
    #if [ $? != 0 ]; then
    if [[ $docker_cur_status =~ $docker_status_running ]] ; then
        # 该符号 =～为：前者字符串包含后者字符串
        echo "docker is running!"
        return 0;
    fi
    echo "docker status is not running， use systemctl start docker or shell to start docker !"
    return 2;
}

function test()
{
    echo "function test"
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
    if [ $# == 2 ]; then
      install $2
    else
      install
    fi
elif [ "${opt}" == "--uninstall" ]; then
    uninstall
elif [ "${opt}" == "--uninstall_all" ]; then
    usage
elif [ "${opt}" == "--start" ]; then
    start
elif [ "${opt}" == "--stop" ]; then
    stop
elif [ "${opt}" == "--status" ]; then
    docker_status
elif [ "${opt}" == "--help" ]; then
    usage
elif [ "${opt}" == "--test" ]; then
    test
else
    echo "Unknown argument"
fi


