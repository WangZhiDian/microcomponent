1 安装linux的jdk，去官网下载jdk的安装包，非rpm形式的包，而是tar.gz形式的包，方便设置配置，好管理。
下载地址，oracle官网地址：
https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html

1）脚本  jdk-manage.sh 配置文件：config.properties  jdk安装包：jdk-8u251-linux-i586.tar.gz
2）上述三个文件需要放在文件夹同一目录下，并且提前填写config中的安装目录和包名称
3）需要在root用户下执行该脚本

该安装可能对部分jdk版本，比如jdk8有效，对高版本的jdk可能无效，因为jdk在/etc/profile中的配置内容可能会变化



