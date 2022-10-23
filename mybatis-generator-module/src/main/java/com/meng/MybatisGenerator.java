package com.meng;


import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MybatisGenerator {

    // 逆向工程自动生成
    // 项目根路径不要有中文，我的有中文，所以使用绝对路径（.src下）
    public void generator(String configFilePath) throws Exception {

        String curDir = System.getProperty("user.dir");

//        String generatorConfigPath = "src/main/java/resources/generatorConfig.xml";
//        String generatorConfigPath = System.getProperty("user.dir") +
//                "/mybatis-generator-module/src/main/resources/generatorConfig.xml";

        String generatorConfigPath = curDir + "/" + configFilePath;
        System.out.println(generatorConfigPath);

        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = new File(generatorConfigPath); // Oracle/MySQL
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    public static void main(String[] args) throws Exception {
        try {
//            System.out.println("参数长度：" + args.length + "   args:" + args[0]);
            String configPath;
            if (args.length != 1) {
                System.out.println("需要一个参数，缺少参数，使用默认参数generatorConfig.xml");
                configPath = "generatorConfig.xml";
            } else {
                configPath = args[0];
            }

            System.out.println("-----------执行开始----------");
            MybatisGenerator startServer = new MybatisGenerator();
            startServer.generator(configPath);
            System.out.println("-----------执行结束----------");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----------执行异常----------");
        }
    }
}
