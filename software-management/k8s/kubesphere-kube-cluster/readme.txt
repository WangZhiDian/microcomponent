Linux多节点部署KubeSphere
1、准备三台服务器
4c8g （master）
8c16g * 2（worker）
centos7.9
内网互通
每个机器有自己域名
防火墙开放30000~32767端口

2、使用KubeKey创建集群
1、下载KubeKey
export KKZONE=cn

curl -sfL https://get-kk.kubesphere.io | VERSION=v1.1.1 sh -

chmod +x kk
2、创建集群配置文件
./kk create config --with-kubernetes v1.20.4 --with-kubesphere v3.1.1

2.5 pre install
yum -y install conntrack

3、创建集群
./kk create cluster -f config-sample.yaml

4、查看进度
kubectl logs -n kubesphere-system $(kubectl get pod -n kubesphere-system -l app=ks-install -o jsonpath='{.items[0].metadata.name}') -f




重启 ks-install
kubectl -n kubesphere-system rollout restart deploy/ks-installer