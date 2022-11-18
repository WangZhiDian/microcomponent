#!/bin/bash

CURRENT_WORK_DIR=$(cd `dirname $0`; pwd)

function usage()
{
    echo "Usage: install.sh [--help]"
    echo ""
    echo "manage k8s with net-work."
    echo ""
    echo "  --help                  : help."
    echo ""
    echo "  --install               : 安装，参数需要masterip，如：--install x.x.x.x"
    echo "  --uninstall             : 卸载k8s."
    echo "  --prepare               : jvm环境预处理，参数需要节点名称.如：--prepare hostname"
    echo "  --master_init           : 初始化.master节点参数需要masterip，如：--master_init x.x.x.x"
    echo "  --calico_install        : 主节点安装calico网络，镜像拉取慢，可以手动安装."
    echo "  --k8s_status            : 查看k8s的安装状态."
    echo ""
    echo "  主节点：prepare，install，master_init，calico_install"
    echo "  子节点：prepare，install，节点加入master"
    echo "  该方式适用于自测集群安装，docker需要提前安装好，各个版本的适配，不同环境可能也有异常"
    echo "  dashboard ingress 的安装代做"


}

function remove_dir()
{
    if [ $# -eq 0 ]; then
        return 0;
    fi

    dir=$1
    if [ ! -d ${dir} ]; then
        echo "no dir "${dir}
    else
        rm -rf $1
    fi
}

function remove_file()
{
    if [ $# -eq 0 ]; then
        return 0;
    fi

    file=$1
    if [ ! -f ${file} ]; then
        echo "no file "${file}
    else
        rm -f $1
    fi
}

function prepare_env()
{
    if [ $# -eq 0 ]; then
        echo "no hostname info"
        exit 0;
    fi
    #设置每个机器自己的hostname
    hostnamectl set-hostname $1

    # 将 SELinux 设置为 permissive 模式（相当于将其禁用）
    sudo setenforce 0
    sudo sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config

    #关闭swap
    swapoff -a
    sed -ri 's/.*swap.*/#&/' /etc/fstab

    #允许 iptables 检查桥接流量
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
br_netfilter
EOF

cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF

    systemctl stop firewalld
    sudo sysctl --system
}

function install()
{
    if [ $# -eq 0 ]; then
        echo "no master ip info"
        exit 0;
    fi

    #配置k8s的yum源地址
cat <<EOF | sudo tee /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=http://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
   http://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

    #安装 kubelet，kubeadm，kubectl
    sudo yum install -y kubelet-1.20.9 kubeadm-1.20.9 kubectl-1.20.9

    #启动kubelet
    sudo systemctl enable --now kubelet

    #所有机器配置master域名
    echo "$1  cluster-endpoint" >> /etc/hosts

sudo tee ./images.sh <<-'EOF'
# 下载节点需要的镜像
images=(
kube-apiserver:v1.20.9
kube-proxy:v1.20.9
kube-controller-manager:v1.20.9
kube-scheduler:v1.20.9
coredns:1.7.0
etcd:3.4.13-0
pause:3.2
)
for imageName in ${images[@]} ; do
    docker pull registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images/$imageName
    done
EOF
    chmod +x ./images.sh && ./images.sh

    return 0
}

function master_init()
{
    # 如果初始化过，需要使用kubeadm reset 重置一下，才能再次初始化
    if [ $# -eq 0 ]; then
        echo "no master ip info"
        exit 0;
    fi

    #init_info=kubeadm init \
    #--apiserver-advertise-address=$1 \
    #--control-plane-endpoint=cluster-endpoint \
    #--image-repository registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images \
    #--kubernetes-version v1.20.9 \
    #--service-cidr=10.96.0.0/16 \
    #--pod-network-cidr=192.168.0.0/16

    init_info=`kubeadm init --apiserver-advertise-address=$1 --control-plane-endpoint=cluster-endpoint --image-repository registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images --kubernetes-version v1.20.9 --service-cidr=10.96.0.0/16 --pod-network-cidr=192.168.0.0/16`

    echo ${init_info} > ${CURRENT_WORK_DIR}/master_init_info.txt

    # 主节点需要执行该配置
    mkdir -p $HOME/.kube
    sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    sudo chown $(id -u):$(id -g) $HOME/.kube/config

    export KUBECONFIG=/etc/kubernetes/admin.conf

    # 获得新的自节点加入kmaster的命令： kubeadm token create --print-join-command
}

function calico_install()
{

#预下载calico需要的镜像，最好手动先下载了
docker pull calico/node:v3.20.6
docker pull calico/cni:v3.20.6
docker pull calico/pod2daemon-flexvol:v3.20.6
docker pull calico/kube-controllers:v3.20.6

    # 下载calico的安装yaml,该版本的k8s需要匹配3.20的calico
    curl https://docs.projectcalico.org/v3.20/manifests/calico.yaml -O
    # 由于calico.yaml里面的docker.io下的镜像下载不了，需要修改yaml文件，从国内的站点下
    sed -i 's#docker.io/##g' calico.yaml

    # 安装calico
    kubectl apply -f calico.yaml
}

function uninstall()
{
    # preparation
    k8s_status
    if [ $? != 0 ]; then
        exit 0;
    fi

    # preparation
    yum -y remove kubelet kubeadm kubectl

    sudo kubeadm reset -f
    sudo rm -rvf $HOME/.kube
    sudo rm -rvf ~/.kube/
    sudo rm -rvf /etc/kubernetes/
    sudo rm -rvf /etc/systemd/system/kubelet.service.d
    sudo rm -rvf /etc/systemd/system/kubelet.service
    sudo rm -rvf /usr/bin/kube*
    #sudo rm -rvf /etc/cni
    #sudo rm -rvf /opt/cni
    sudo rm -rvf /var/lib/etcd
    sudo rm -rvf /var/etcd

    echo "Uninstall k8s service success."
}


function k8s_status()
{

    if [ ! -f '/usr/bin/kubeadm' ] || [ ! -f '/usr/bin/kubectl' ] || [ ! -f '/usr/bin/kubelet' ]; then
        echo "k8s not install"
        return 1;
    fi

    #docker_status_running="running"
    #docker_cur_status=`systemctl status docker | grep '(running)'`
    #if [ $? != 0 ]; then
    #if [[ $docker_cur_status =~ $docker_status_running ]] ; then
        # 该符号 =～为：前者字符串包含后者字符串
     #   echo "docker is running!"
      #  return 0;
    #fi
    return 0;
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
    install $2
elif [ "${opt}" == "--uninstall" ]; then
    uninstall
elif [ "${opt}" == "--prepare" ]; then
    prepare_env $2
elif [ "${opt}" == "--master_init" ]; then
    master_init $2
elif [ "${opt}" == "--calico_install" ]; then
    calico_install
elif [ "${opt}" == "--k8s_status" ]; then
    k8s_status
elif [ "${opt}" == "--help" ]; then
    usage
elif [ "${opt}" == "--test" ]; then
    test
else
    echo "Unknown argument"
fi


