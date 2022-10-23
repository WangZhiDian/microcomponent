1 mybatis-module 参考说明文档：https://blog.csdn.net/qq_19387933/article/details/123256034
    结论：可以生成部分mybatis语法和传参用法，写代码时可以当工具类参考

2 mybatis-generator-module :参考自动反射数据库对象
    结论：可以生成只依赖jvm和配置文件就可以运行的jar包来反射数据表对象

3 jrebel插件等使用
    1）下载插件JRebel ：https://plugins.jetbrains.com/plugin/4441-jrebel-and-xrebel-for-intellij
        或者：https://plugins.jetbrains.com/plugin/download?updateId=25523
    2）在idea中离线安装插件
    3）mac上等离线激活，免费的方式：https://www.cnblogs.com/shisanye/p/16418289.html
       在可以访问外网的docker环境中拉取如下镜像，并启动：
       docker pull ilanyu/golang-reverseproxy
       docker run -d -p 8888:8888 ilanyu/golang-reverseproxy
    4）网上生成一个uuid：https://www.guidgen.com/
    5）idea上激活时，url填写：http://{ip}:8888/{uuid} ，然后随便输入一个格式对对邮箱，点击激活就可以了
    结论：建议使用

4 RestfulToolKit 插件工具对安装和使用：直接百度就可以了
    该工具可以直接在idea中模拟简单的http接口访问，做测试，不用启动postman，但同时可编辑性和移植性也没那么方便
    结论：可用可不用
