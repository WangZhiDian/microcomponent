安装docker的一些前置依赖条件：https://www.cnblogs.com/sugartang/p/13290236.html

机器没有epel等基础环境：

查看配置基本情况：
1 查看内核：
uname -a
cat /proc/version

2 查看当前系统版本的详细信息
cat /etc/redhat-release(此方法只适合Redhat 系的Linux)
lsb_release -a (此命令适用于所有的Linux 发行版本）

3 依次运行以下命令添加yum
yum update
yum install epel-release -y
yum clean all
yum list

4





