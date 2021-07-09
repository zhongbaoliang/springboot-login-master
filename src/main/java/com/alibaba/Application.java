package com.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @SpringBootApplication相当于3个注解——三体结构
 * 1. @Configuration  定义一个配置类
 * 2. @EnableAutoConfiguration spring boot自动根据jar包的依赖来自动配置项目
 * 3. @ComponentScan  spring自动扫描类上的注解, 并注入spring容器
 *
 * 可以用这三个注解直接替换 @SpringBootApplication，程序正常运行
 */

/**
 * @SpringBootApplication
 * 标注这个类是一个springboot的应用
 * @SpringBootConfiguration ： springboot的配置
 *      @Configuration : spring的配置类
 *          @Component：说明这也是一个spring组件
 *
 *
 * @EnableAutoConfiguration ： 自动配置文件
 *      @AutoConfigurationPackage ： 自动配置包
 *          @Import({Registrar.class}) ： 导入注册器
 *      @Import({AutoConfigurationImportSelector.class}) ： 导入选择器
 *
 *
 * @ComponentScan() 扫描()里面的包，可以剔除一些东西
 *
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //将springboot应用启动
        //参数为 本类的类对象 和 命令参数
        SpringApplication.run(Application.class,args);
    }

}
