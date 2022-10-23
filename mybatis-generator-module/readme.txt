使用mybatis的generator逆向生成源代码
方式一： 通过idea工程配置，1 引入必要的maven依赖，2 引入plugin配置，3 创建generator-config.xml文件
并修改为自己的表相关信息，4 双击右侧Plugins中的mybatis-generator下的mybatis-generator：generate
直接生成反射文件

方式二：通过代码生成，可以自定义一些参数内容
1 引入必要maven依赖和plugin， 2 创建generator-config.xml文件， 3 拷贝MybatisGenerator类中的代码
4 运行main文件生成。
弊端：该方式，还是需要将配置和代码嵌入到idea工程中

方式三：通过mybatis-generator-core-1.3.2.jar文件生成
该方式使用的是mybatis提供的直接运行的jar来生成

方式四：该方式类似方式二和方式三的叠加，将方式二中的代码，
1 打包成一个可运行的jar包，直接在jvm环境上 运行 java -jar xxx.jar 来生成反射；
2 创建generator-config.xml文件，并且和步骤1中的jar放在同一个目录中；
特点：该方式比较灵活，且自己可以修改代码打包或者设置配置文件等，并且不用嵌入到工程中



问题：使用代码执行时，注意打包插件配置，如果有错误可能找不到执行类
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>1.5.3.RELEASE</version>
    <configuration>
        <mainClass>com.xx.webapps.api.main.WebappsApiBidMain</mainClass>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>