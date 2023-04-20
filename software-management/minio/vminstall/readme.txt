
1 创建minio.service，内容见minio.service
 vim /etc/systemd/system/minio.service

2 创建用户和赋权：
groupadd -r minio-user
useradd -M -r -g minio-user minio-user
chown minio-user:minio-user /mnt/disk1 /mnt/disk2 /mnt/disk3 /mnt/disk4


3 创建 /etc/default/minio, 内容见minio
vim  /etc/default/minio



/usr/local/bin/minio server --console-address :9001 \
http://minio{1...2}.example.com:9000/opt/minio/data

./minio server --console-address :9001 http://minio{1...2}.example.com:9000/minio


export MINIO_ROOT_USER=admin
export MINIO_ROOT_PASSWORD=admin123
/opt/minio/app/minio server --address ":9000" --console-address ":9001" \
http://172.16.40.70/minio/data1 \
http://172.16.40.70/minio/data2 \
http://172.16.40.71/minio/data1 \
http://172.16.40.71/minio/data2


# 可行安装命令（前提，磁盘非root同一块磁盘，为独立挂载）
export MINIO_ROOT_USER=admin
export MINIO_ROOT_PASSWORD=admin123
/opt/minio/minio server --address ":9000" --console-address ":9001" \
http://172.16.40.61/minio/data1 \
http://172.16.40.61/minio/data2 \
http://172.16.40.62/minio/data1 \
http://172.16.40.62/minio/data2


/opt/minio/app/minio server --address ":9000" --console-address ":9001"
http://172.16.40.70/minio/data1
http://172.16.40.70/minio/data2
http://172.16.40.71/minio/data1
http://172.16.40.71/minio/data2



export MINIO_ROOT_USER=admin
export MINIO_ROOT_PASSWORD=admin123
/opt/minio/app/minio server --address ":9000" --console-address ":9001" http://172.16.40.70/minio/data1 http://172.16.40.70/minio/data2 http://172.16.40.71/minio/data


注意： 需要挂载新的磁盘，来挂载minio的存储目录，才能部署minio的集群
如果使用root的磁盘对应的分区和磁盘来部署集群，会失败，会提示 is part of root drive /Insufficient number of drives online 等错误