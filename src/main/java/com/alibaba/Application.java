package com.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
 * @EnableAutoConfiguration ： 启动自动配置导入选择器
 *      @AutoConfigurationPackage ： 自动配置包
 *          @Import({Registrar.class}) ： 导入注册器
 *      @Import({AutoConfigurationImportSelector.class}) ： 导入选择器
 *
 *
 * @ComponentScan() 扫描()里面的包，可以剔除一些东西，然后加载包里面符合条件的bean
 *
 *
 */
@SpringBootApplication
//@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        //将springboot应用启动
        //参数为 本类的类对象 和 命令参数
        SpringApplication.run(Application.class,args);
    }

}
