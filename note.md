![structure](.\src\main\resources\img\structure.jpg)

# Spring 

## Spring IOC

### Spring提供两种IOC容器

**BeanFactory和ApplicationContext**；ApplicationContext是BeanFactory的子接口。二者都是通过XML文件配置加载bean的。

二者的主要区别在于 **注入属性的检查时机** 不同，BeanFactory在第一次使用getBean方法时，而ApplicationContext在初始化时。因此实际开发中通常使用ApplicationContext，而只有在系统资源较少时才考虑使用BeanFactory。

```java
BeanFactory beanFactory=new XmlBeanFactory(new FileSystemResource("D://applicationContext.xml"));
```



```java
ApplicationContext applicationContext=new ClassPathXmlApplicationCoantext("D://applicationContext.xml");
```



### Spring DI（依赖注入）的实现方式

**构造方法和set方法注入**：即自行创建和参数获取两种方式。



### Spring Bean

​	构建应用程序的主干，由Spring IOC 管理的对象成为bean。Bean及其之间的依赖关系反映在IOC容器使用的“配置元数据”中。Spring的核心机制依赖注入，就是组件的实例化不再由程序完成，而是转交给Spring容器完成，在需要时注入到应用程序中，从而对组件之间的依赖关系进行了解耦。这一切都离不开配置文件中使用的<bean>元素。Spring配置文件支持xml和properties两种格式。通常使用xml作为配置文件。

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.2.xsd">
    <!-- 使用id属性定义person1，其对应的实现类为com.mengma.person1 -->
    <bean id="person1" class="com.mengma.damain.Person1" />
    <!--使用name属性定义person2，其对应的实现类为com.mengma.domain.Person2-->
    <bean name="Person2" class="com.mengma.domain.Person2"/>
</beans>
```

<bean>的重要属性：

| 属性名称            | 描述                                                         |
| ------------------- | ------------------------------------------------------------ |
| **id**              | 是一个 Bean 的唯一标识符，Spring 容器对 Bean 的配置和管理都通过该属性完成 |
| **name**            | Spring 容器同样可以通过此属性对容器中的 Bean 进行配置和管理，name 属性中可以为 Bean 指定多个名称，每个名称之间用逗号或分号隔开 |
| **class**           | 该属性指定了 Bean 的具体实现类，它必须是一个完整的类名，使用类的全限定名 |
| **scope**           | 用于设定 Bean 实例的作用域，其属性值有 singleton（单例）、prototype（原型）、request、session 和 global Session。其默认值是 singleton |
| **constructor-arg** | <bean>元素的子元素，可以使用此元素传入构造参数进行实例化。该元素的 index 属性指定构造参数的序号（从 0 开始），type 属性指定构造参数的类型 |
| **property**        | <bean>元素的子元素，用于调用 Bean 实例中的 Set 方法完成属性赋值，从而完成依赖注入。该元素的 name 属性指定 Bean 实例中的相应属性名 |
| **ref**             | <property> 和 <constructor-arg> 等元素的子元索，该元素中的 bean 属性用于指定对 Bean 工厂中某个 Bean 实例的引用 |
| **value**           | <property> 和 <constractor-arg> 等元素的子元素，用于直接指定一个常量值 |
| list                | 用于封装 List 或数组类型的依赖注入                           |
| set                 | 用于封装 Set 类型属性的依赖注入                              |
| map                 | 用于封装 Map 类型属性的依赖注入                              |
| entry               | <map> 元素的子元素，用于设置一个键值对。其 key 属性指定字符串类型的键值，ref 或 value 子元素指定其值 |

​	bean的属性配置又称为bean的**元数据配置**。

### Bean作用域

Spring 容器在初始化一个 Bean 的实例时，同时会指定该实例的作用域。Spring3 为 Bean 定义了五种作用域，具体如下。

#### 1）singleton

​	单例模式，使用 singleton 定义的 Bean 在 Spring 容器中只有一个实例，这也是 Bean 默认的作用域。

#### 2）prototype

​	原型模式，每次通过 Spring 容器获取 prototype 定义的 Bean 时，容器都将创建一个新的 Bean 实例。

#### 3）request

​	在一次 HTTP 请求中，容器会返回该 Bean 的同一个实例。而对不同的 HTTP 请求，会返回不同的实例，该作用域仅在当前 HTTP Request 内有效。

#### 4）session

​	在一次 HTTP Session 中，容器会返回该 Bean 的同一个实例。而对不同的 HTTP 请求，会返回不同的实例，该作用域仅在当前 HTTP Session 内有效。

#### 5）global Session

​	在一个全局的 HTTP Session 中，容器会返回该 Bean 的同一个实例。该作用域仅在使用 portlet context 时有效。

### Bean实例化方式

​		spring中实例化bean有三种方式：构造器实例化、静态工厂实例化、实例工厂方式。

**构造器实例化**：

```java
//实体类
package com.mengma.instance.constructor;
public class Person1 {
}
```

```xml
//Spring配置文件
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.2.xsd">
    <bean id="person1" class="com.mengma.instance.constructor.Person1" />
</beans>

```

```java
//测试类
package com.mengma.instance.constructor;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class InstanceTest1 {
    @Test
    public void test() {
        // 定义Spring配置文件的路径
        String xmlPath = "com/mengma/instance/constructor/ApplicationContext.xml";
        // 初始化Spring容器，加载配置文件，并对bean进行实例化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                xmlPath);
        // 通过容器获取id为person1的实例
        System.out.println(applicationContext.getBean("person1"));
    }
}
```

​		定义了 Spring 配置文件的路径，然后 Spring 容器会加载配置文件。***在加载的同时，Spring 容器会通过实现类 Person1 中默认的无参构造函数对 Bean 进行实例化***。

**创建静态工厂类**

```java
package com.mengma.instance.static_factory;
public class Person2 {
}
```

```java
package com.mengma.instance.static_factory;
public class MyBeanFactory {
    // 创建Bean实例的静态工厂方法
    public static Person2 createBean() {
        return new Person2();
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.2.xsd">
    <bean id="person2" class="com.mengma.instance.static_factory.MyBeanFactory"
        factory-method="createBean" />
</beans>
```

```java
package com.mengma.instance.static_factory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class InstanceTest2 {
    @Test
    public void test() {
        // 定义Spring配置文件的路径
        String xmlPath = "com/mengma/instance/static_factory/applicationContext.xml"; // 初始化Spring容器，加载配置文件，并对bean进行实例化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                xmlPath);
        // 通过容器获取id为person2实例
        System.out.println(applicationContext.getBean("person2"));
    }
}
```

​	xml中 class 属性指定了其对应的工厂实现类为 MyBeanFactory，而 factory-method 属性用于告诉 Spring 容器调用工厂类中的 createBean() 方法获取 Bean 的实例。

**实例化工厂**

​	工厂类不再使用静态方法创建Bean实例，而是直接在成员方法中创建bean实例。

```java
//实体类
package com.mengma.instance.factory;
public class Person3 {
}
```

```java
package com.mengma.instance.factory;
public class MyBeanFactory {
    public MyBeanFactory() {
        System.out.println("person3工厂实例化中");
    }
    // 创建Bean的方法
    public Person3 createBean() {
        return new Person3();
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.2.xsd">
    <!-- 配置实例工厂 -->
    <bean id="myBeanFactory" class="com.mengma.instance.factory.MyBeanFactory" />
    <!-- factory-bean属性指定一个实例工厂，factory-method属性确定使用工厂中的哪个方法 -->
    <bean id="person3" factory-bean="myBeanFactory" factory-method="createBean" />
</beans>
```



### Bean生命周期

![image-20210701164602402](.\src\main\resources\img\image-20210701164602402.png)

​	实例化->DI设置属性->初始化->单例模式则spring管理bean，原型模式则调用者管理bean

### Bean装配方式

​	Bean的装配也就是Bean的依赖注入方式。主要有基于XML方式和纯Java方式。

#### 基于XML装配

主要是set方法装配和构造方法装配、注解（Annotation）方式、自动（AutoWrite）装配方式。

##### set方法和构造方法

​	使用构造方法注入时，在xml中使用<bean>元素的子元素<constructor-arg>标签定义构造方法的参数。

​	使用set方法注入时，在xml中使用<bean>元素的子元素<properties>元素为每个属性设置值。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.2.xsd">
    <!-- 使用set方法方式装配Person实例 property对应set方法-->
    <bean id="person1" class="com.mengma.assembly.Person">
        <property name="name" value="zhangsan" />
        <property name="age" value="20" />
    </bean>
    <!-- 使用构造方法装配Person实例 constructor-arg对应构造方法，index对应参数的顺序-->
    <bean id="person2" class="com.mengma.assembly.Person">
        <constructor-arg index="0" value="lisi" />
        <constructor-arg index="1" value="21" />
    </bean>
</beans>
```

##### 注解装配

​	尽管使用xml文件可以实现Bean的装配，但是如果应用中Bean的数量太多，会导致xml文件过于臃肿，从而给维护和升级带来一定的困难。可以通过@scope注解设置bean作用域。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
            http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
            http://www.springframework.org/schema/aop
            http://www.springframework.org/schema/aop/spring-aop-2.5.xsd
            http://www.springframework.org/schema/tx
            http://www.springframework.org/schema/tx/spring-tx-2.5.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd">
    <!--使用context命名空间，通知spring扫描指定目录，进行注解的解析-->
    <context:component-scan base-package="com.mengma.annotation"/>
</beans>
```



###### Spring的常用注解

1. **@Component**  可以使用此注解描述 Spring 中的 Bean，但它是一个泛化的概念，仅仅表示一个组件（Bean），并且可以作用在任何层次。使用时只需将该注解标注在相应类上即可。
2. **@Repository**  用于将数据访问层（DAO层）的类标识为 Spring 中的 Bean，其功能与 @Component 相同。
3. **@Service**  通常作用在业务层（Service 层），用于将业务层的类标识为 Spring 中的 Bean，其功能与 @Component 相同。
4. **@Controller**  通常作用在控制层（如 [Struts2](http://c.biancheng.net/struts2/) 的 Action），用于将控制层的类标识为 Spring 中的 Bean，其功能与 @Component 相同。
5. **@Autowired**  用于对 Bean 的属性变量、属性的 Set 方法及构造函数进行标注，配合对应的注解处理器完成 Bean 的自动配置工作。**默认按照 Bean 的类型**进行装配。
6. **@Resource**  其作用与 Autowired 一样。其区别在于 @Autowired 默认按照 Bean 类型装配，而 @Resource 默认按照 Bean 实例名称进行装配。@Resource 中有两个重要属性：name 和 type。Spring 将 name 属性解析为 Bean 实例名称，type 属性解析为 Bean 实例类型。如果指定 name 属性，则按实例名称进行装配；如果指定 type 属性，则按 Bean 类型进行装配。如果都不指定，则**先按 Bean 实例名称装配，如果不能匹配，则再按照 Bean 类型进行装配**；如果都无法匹配，则抛出 NoSuchBeanDefinitionException 异常。
7. **@Qualifier** 与 @Autowired 注解配合使用，会将默认的按 Bean 类型装配修改为按 Bean 的实例名称装配，Bean 的实例名称由 @Qualifier 注解的参数指定。
8. **@Bean**  与<bean/>作用一样，都是用于类的实例化，配置并初始化为Spring IOC容器里面的一个对象。
9.  **@Configration**   表示该类的主要目的是作为 Bean 定义的来源。
10. **@RequestMapping** Spring MVC 中使用 @RequestMapping 来映射请求，也就是通过它来指定控制器可以处理哪些URL请求。可被**@GetMapping、@PostMapping、@PutMapping、@DeleteMapping、@PatchMapping**注解替换，例如：@RequestMapping(value="/get/{id}",method=RequestMethod.GET)=@GetMapping("/get/{id}")。
11. **@ResponseBody**  将java对象转为json格式的数据。
12.  **@RestController**  @Controller + @ResponseBody，主要是为了使 http 请求返回 json 或者xml格式数据，一般情况下都是使用这个注解。
13. @Valid 用于对象属性字段的规则检测。

##### 自动装配

​	自动装配就是指Spring容器可以自动装配（autowire）相互协作的Bean之间的关联方式，将一个Bean注入到其他的Property中。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:p="http://www.springframework.org/schema/p" 
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="  
            http://www.springframework.org/schema/beans 
            http://www.springframework.org/schema/beans/spring-beans-2.5.xsd  
            http://www.springframework.org/schema/aop 
            http://www.springframework.org/schema/aop/spring-aop-2.5.xsd  
            http://www.springframework.org/schema/tx 
            http://www.springframework.org/schema/tx/spring-tx-2.5.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd">
    <bean id="personDao" class="com.mengma.annotation.PersonDaoImpl" />
    <bean id="personService" class="com.mengma.annotation.PersonServiceImpl"
        autowire="byName" />
    <bean id="personAction" class="com.mengma.annotation.PersonAction"
        autowire="byName" />
</beans>
```

​	上述配置文件中，personService和personAction的<bean>元素中除了id和class外，还有autowrite属性，并将属性值设置为byName，即按属性名称自动装配。

aotowrite属性值可以为：

| 名称        | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| **byName**  | **根据 Property 的 name 自动装配，如果一个 Bean 的 name 和另一个 Bean 中的 Property 的 name 相同，则自动装配这个 Bean 到 Property 中。** |
| **byType**  | **根据 Property 的数据类型（Type）自动装配，如果一个 Bean 的数据类型兼容另一个 Bean 中 Property 的数据类型，则自动装配。** |
| constructor | 根据构造方法的参数的数据类型，进行 byType 模式的自动装配。   |
| autodetect  | 如果发现默认的构造方法，则用 constructor 模式，否则用 byType 模式。 |
| no          | 默认情况下，不使用自动装配，Bean 依赖必须通过 ref 元素定义。 |



#### 纯Java方式装配

下面两种方式等价

```java
@Configuration
public class MockConfiguration {
    @Bean
    public MockService mockService() {
        return new MockServiceImpl(dependencyService());
    }
    @Bean
    public DependencyService dependencyService() {
        return new DependencyServiceImpl();
    }
}
```



```xml
<bean id="mockService" class="..MockServiceImpl">    
    <property name="dependencyService" ref="dependencyService" /></bean>
<bean id="dependencyService" class="DependencyServiceImpl" /> 
```





### Spring IOC容器依赖注入流程

**Spring IOC容器的依赖注入工作可以分为两个阶段**：

1. 构建收集注册bean阶段：通过XML或者Java代码的方式定义一些bean, 然后通过手动组装（XML)文件中（Bean装配方式1 xml），或者让容器基于某些机制自动扫描的形式（Bean装配方式2 注解），将这些**bean定义收集到IOC容器**中。
2. 分析组装阶段：第一阶段完成后IOC容器中充斥着一个个独立的Bean，他们之间没有任何关系，但实际上他们之间是有**依赖关系**的，这一阶段的作用就是：当IOC容器发现bean1依赖于bean2时，就将bean2注入给bean1，知道所有bean依赖都注入完成。



## AOP

​	面向切面编程（AOP）类似于OOP也是一种编程模式。Spring AOP的使用**减少了系统间的重复代码，达到了模块间松耦合的目的**。它将业务逻辑的各个部分分隔开，是程序员在编写业务代码时能够专心于核心业务，从而提高开发效率。

​	AOP采取横向抽取机制取代了传统的纵向继承机制，减少系统间的重复代码。

​	例如传统OOP中 通过一个类Access 对一个资源resource进行加锁解锁控制，要访问这个资源就需要继承Access类，而java只支持单继承（解决多继承中函数冲突问题），导致另外一个父类需要重复写入Access中的方法。而AOP可以将这一模块横向提取出来，提高代码复用，降低耦合。主要应用于日志管理、权限控制、异常处理等方面（百度百科举例)。

​	过滤器、拦截器、监听器都属于面向切面编程的具体实现。

![image-20210712205442787](./src/main/resources/img/AOP.jpg)

### Spring AOP术语

横切关注点：跨越应用程序多个模块的方法和功能。即与业务逻辑无关的，但是需要关注的部分。如：日志，安全，缓存，事务等**公共功能**。

切面（ASPECT）：横切关注点 被模块化的特殊对象，他是一个类，**如Log类。**

通知（Advice）：切面必须要完成的工作，即一个方法，**如Log类中的写日志方法。**

目标（Target）：被通知对象，**即被代理类。**

代理（Proxy）：向目标对象应用通知之后创建的对象，即·通过Log类和目标类生成的**代理类**。

切入点（PointCut）：切面通知 执行的 位置。

连接点（joinPoint）：与切入点匹配的执行点。

增强：

前置通知、后置通知、异常通知、返回通知、环绕通知







### 作用时机

#### 过滤器

​	服务启动

​	服务结束

​	每个请求

#### 拦截器

​	作用域controller函数前后，或者MVC渲染之后。

​	controller执行之前

​	controller执行之后，但是在渲染之前

​	渲染之后

#### 监听器

​	可作用于整个服务期间

#### 切面

​	作用于业务层代码（函数）前后。

​	前置增强：在业务代码执行前

​	后置增强：方法结束后（无论是正常执行完，还是抛出异常)

​	环绕增强：目标方法执行前后增强

​	异常抛出增强：目标方法抛出异常后增强

​	引介增强：在目标类中添加新的方法属性

```java
@After("execution(* com.alibaba.aspect.aopTest1.service.UserService1Impl.*(..))")
```

​	@After表明通知（增强方法）位于连接点（被增强的方法）之前，execution表明连接点（被增强方法）的位置，以 *  号开头，最后面的*表示匹配目标（被代理类）任意方法，(..)表示任意参数。

过滤器与拦截器区别：

所属范畴不同：过滤器属于JavaEE，拦截器属于SpringMVC

实现方式不同：过滤器基于回调函数，拦截器基于反射机制

作用时机不同：过滤器作用于每个请求执行前或者服务开始与结束时,拦截器作用于MVC两个阶段前中后

作用范围不同：过滤器适用于所有请求，拦截器只适用于SpringMVC的action。



### 代理模式与回调函数

​	

回调函数举例：

```java
interface Callback {
    void execute(String session);//被调函数
}
//主调类
class Host {
    private String mySession = "I'm session";
    //主调函数，将接口作为参数，可以传入接口的任意实现类。
    public void method (Callback callback) {
        System.out.println("before");
        callback.execute(mySession);//同步回调
        new Thread(()->{//异步回调
            callback.execute(mySession);
        }).start();
        System.out.println("after");
    }
}
//被调类,实现接口
class Guest implements Callback{

    @Override
    public void execute(String session) {
        System.out.println(session);
    }
}

public class Test {
    public static void main(String[] args) {
        new Host().method(new Guest());
    }
}
```



代理模式举例：



```java
package proxy;
 
//被代理类（目标）
class Model implements DoSomething {
	@Override
	public void doIt() {
		System.out.println("do it");
	}
}
 
//代理类（切面）
class Proxy implements DoSomething {
	//代理类拥有接口（可传入被代理类）的引用
	private DoSomething doSomething;
	public Proxy (DoSomething doSomething) {
		this.doSomething = doSomething;
	}
    public void setTarget(DoSomething doSomething) {
		this.doSomething = doSomething;
	}
	
    //方法增强（通知）
	@Override
	public void doIt() {
		System.out.println("before");
		doSomething.doIt();
		System.out.println("after");
	}
}

//代理类与被代理类实现同一个接口
interface DoSomething {
	void doIt();
}

public class Test {
	public static void main(String[] args) {
		DoSomething doSomething = new Model();
		doSomething = new Proxy(doSomething);
		doSomething.doIt();
	}
}
```

回调函数与代理模式关系：

相同点：

​	都是AOP思想的实现。

不同点：

​	代理模式基于接口时，代理类和被代理类**都实现相同的接口**，代理类中拥有接口（**被代理类）的引用**。

​	而回调函数中，**只有被调类需要实现接口**（接口中的回调方法），主调类中**主调方法的参数是接口**，实际上传入的是被调类。

​	

​	个人认为：两者侧重点不同，代理模式主要是为了实现提取公共业务，对每个业务进行逻辑增强。而回调函数主要是可以实现异步处理。





## 事务

​	事务是用来确保数据的完整性和一致性。

### ACID

- 原子性（Atomicity）：一个事务是一个不可分割的工作单位，事务中包括的动作要么都做要么都不做。
- 一致性（Consistency）：事务必须保证数据库从一个一致性状态变到另一个一致性状态，一致性和原子性是密切相关的。
- 隔离性（Isolation）：一个事务的执行不能被其它事务干扰，即一个事务内部的操作及使用的数据对并发的其它事务是隔离的，并发执行的各个事务之间不能互相打扰。
- 持久性（Durability）：持久性也称为永久性，指一个事务一旦提交，它对数据库中数据的改变就是永久性的，后面的其它操作和故障都不应该对其有任何影响。



### Spring中的事务

Spring 的事务管理有 2 种方式：

1. 传统的**编程式事务**管理，即通过编写代码实现的事务管理；
2. 基于 AOP 技术实现的**声明式事务**管理。

#### 1. 编程式事务管理

​	编程式事务管理是通过编写代码实现的事务管理，灵活性高，但难以维护。

#### 2. 声明式事务管理

​	Spring 声明式事务管理在底层采用了 AOP 技术，其最大的优点在于无须通过编程的方式管理事务，只需要在配置文件中进行相关的规则声明，就可以将事务规则应用到业务逻辑中。

Spring 实现声明式事务管理主要有 2 种方式：

- 基于 XML 方式的声明式事务管理。

- 通过 Annotation 注解方式的事务管理。

  显然声明式事务管理要优于编程式事务管理。



## 事务管理接口

​	PlatformTransactionManager、TransactionDefinition 和 TransactionStatus 是事务的 3 个核心接口。

### PlatformTransactionManager接口

​	PlatformTransactionManager 接口用于管理事务。

```java
public interface PlatformTransactionManager {
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;
    //用于获取事务的状态
    void commit(TransactionStatus status) throws TransactionException;
    //用于提交事务
    void rollback(TransactionStatus status) throws TransactionException;
    //用于回滚事务
}
```

​	Spring 将 xml 中配置的事务信息封装到对象 TransactionDefinition 中，然后通过事务管理器的 getTransaction() 方法获得事务的状态（TransactionStatus），并对事务进行下一步的操作。



### TransactionDefinition接口

​	TransactionDefinition 接口用于获取事务相关信息。

```java
public interface TransactionDefinition {
    int getPropagationBehavior();
    int getIsolationLevel();
    String getName();//获取事务名称
    int getTimeout();
    boolean isReadOnly();
}
```







# 设计模式

## 代理模式

​	代理类A帮被代理类B做事，隐藏被代理类的信息。分为静态代理和动态代理。在代理类中可以对被代理类进行逻辑增强。

### 静态代理

​	代理类和被代理类实现了同一个接口，代理类持有被代理类的实例。

结构：

​	interface{func()}

​	role implements interface{实现func()}

​	proxy implements interface{实现func()}

优点：

​	可以使真是角色的操作更加纯粹，不必关注一些公共的业务（如：余额不足）。

​	公共功能交给你代理类，实现业务分工（逻辑增强）。

​	公共业务发生扩展时，方便集中管理。

缺点：

​	一个真实的角色就会产生一个代理角色，代码量翻倍。



### 动态代理

​	动态代理和静态代理一样需要一个接口和一个被代理类。

​	代理类是自动动态生成的。

​	动态代理分为两大类：基于接口的动态代理，基于类的动态代理。

​		基于接口：JDK动态代理，被代理类实现一个接口，生成的代理类是这个**接口的实现类**。

​		基于类：cglib，生成的代理类是**被代理类的子类**。

​		基于java字节码：javasist

​	JDK的动态代理在java.lang.reflect包下，基于其中两个类来实现：

​		Proxy

​		InvocationHandler

动态代理的优点：

​	可以使真是角色的操作更加纯粹，不必关注一些公共的业务（如：余额不足）。

​	公共功能交给你代理类，实现业务分工（逻辑增强）。

​	公共业务发生扩展时，方便集中管理。

​	一个动态代理类代理的是一个1接口，一般是对应一类业务。

​	一个动态代理类可以代理多个类，只要这些类实现了同一个接口。



# 密码

密钥越长越安全，越短效率越高。	

​	对称加密：加密密钥与解密密钥一样，效率高。但是一般密码比较短，安全性不够。如DES、AES等

​	非对称加密：加密密钥与解密密钥不同，使用可以公开的公钥进行加密，使用私钥进行解密。其特点是效率低，但是安全性高。如RSA

​	**使用非对称密码算法的公钥对 对称密码算法的密钥进行解密**

（1） Alice需要在银行的网站做一笔交易，她的浏览器首先生成了一个随机数作为对称密钥。

（2） Alice的浏览器向银行的网站请求公钥。

（3） 银行将公钥发送给Alice。

（4） Alice的浏览器使用银行的公钥将自己的对称密钥加密。

（5） Alice的浏览器将加密后的对称密钥发送给银行。

（6） 银行使用私钥解密得到Alice浏览器的对称密钥。

（7） Alice与银行可以使用对称密钥来对沟通的内容进行加密与解密了。





# Spring MVC

## 简介

​	Spring MVC是Spring提供的一个灵活而强大的web框架。借助于注解，Spring MVC提供了几乎是POJO的开发模式，使得控制器的开发和测试更加简单。这些**控制器一般不直接处理请求，而是将请求委托给spring上下文中的其他的bean**，通过spring的依赖注入功能，这些bean被注入到控制器中。

​	Spring MVC主要由**DispatcherServlet、处理器映射、处理器（控制器）、视图解析器、视图** 五部分组成。其核心是处理器映射和视图解析器，前者负责选择出哪个控制器来处理哪个请求，后者负责结果渲染；通过这两个模块，spring MVC保证了 如何选择控制处理请求 和 如何选择视图展现输出 之间的松耦合。

## 流程图



![SpringMVC-1](.\src\main\resources\img\SpringMVC-1.jpg)

![springmvc2](.\src\main\resources\img\springmvc2.jpg)

## MVC工作流程

1. Http请求：客户端请求提交到DispatcherServlet
2. 寻找处理器：由DispatcherServlet控制器查询一个或者多个HandlerMapping，找到处理请求的Controller。
3. 调用处理器：DispatcherServlet将请求提交给对应的controller。
4. 调用业务代码对请求进行处理。
5. 获取处理结果：主要是 视图名 和 业务处理结果。
6. 处理视图映射：通过视图名定位到对应的视图，如index.html。
7. 将模型数据传给View显示：将数据渲染到视图上。
8. Http相应：将视图结果发送到客户浏览器。



## 主要功能模块

DispatcherServlet：前端控制器，所有的请求都经过它统一分发。

HandlerMapping： 处理器映射，将请求映射到Controller。

Controller：控制器，并发地为用户的请求做处理。

ViewResolver：视图解析器，将模型（处理的结果数据）渲染到视图（jsp、html等）上。



## JSON

前进后端分离：

​	后端：提供接口（controller中对应的action），提供数据，业务逻辑。

​	JSON：连接桥梁，是一种数据交换格式

​	前端：渲染数据，提供交互。



JSON格式：

​	对象表示为键值对

​	数据由逗号分割

​	花括号保存对象

​	方括号保存数组

```json
{
	key1: value1,
	key2: value2,
	...
}
```

任何数据类型都可以用JSON表示，如：字符串，数字，对象，数组等等。	

例如：

```json
{
	"name": "中国",
    "age": 5000,
    "province": [
        {
            "name": "湖北",
            "city": {
                "name": "武汉",
                "position": "洞庭湖北部"
            }
        },
        
        {
            "name": "湖南",
            "city": {
                "name": "长沙",
                "position": "洞庭湖南部"
            }
        }
    ]
}
```

​	**JSON是JS对象的字符串表示法。**

```js
var obj = {a: "hello",b: "world"};
//JS对象，key可以加引号也可以不加
```

```js
var obj = JSON.parse('{"a": "Hello", "b": "world"}');
//JSON字符串转JS对象，使用JSON.parse()
var json = JSON.stringify({a: 'hello', b: 'world'});
//JS对象转JSON字符串，使用JSON.stringify()
```



## 总结

​	简而言之，springmvc就是**选择处理器与渲染视图**两大功能。









# Spring Boot



## 新增注解



**@ComponentScan**   对应 XML 配置形式中的 <context：component-scan> 元素，用于配合一些元信息 Java Annotation，比如 @Component 和 @Repository 等，**将标注了这些元信息 Annotation 的 bean 定义类批量采集到 Spring 的 IoC 容器中。**我们可以通过 basePackages 等属性来细粒度地定制 @ComponentScan 自动扫描的范围，如果不指定，则默认 Spring 框架实现会从声明 @ComponentScan 所在类的 package 进行扫描。



**@PropertySource 与 @PropertySources** @PropertySource **用于从某些地方加载 *.properties 文件内容，并将其中的属性加载到 IoC 容器**中，便于填充一些 bean 定义属性的占位符（placeholder）。使用 Java 8 或者更高版本开发，那么可以并行声明多个 @PropertySource。使用低于 Java 8 版本的 Java 开发 Spring 应用，又想声明多个 @PropertySource，则需要借助 @PropertySources 的帮助

```java
@Configuration
@PropertySource("classpath:1.properties")
@PropertySource("classpath:2.properties")
@PropertySource("...")
public class XConfiguration{
    ...
}
```



```java
@PropertySources({ @PropertySource("classpath:1.properties"), @PropertySource("classpath:2.properties"), ...})
public class XConfiguration{
    ...
}
```

**@Import 与 @ImportResource**  将括号里面的类中定义的bean加载进IOC容器。相当于在 XML 形式的配置中，通过 <import resource="XXX.xml"/> 的形式**将多个分开的容器配置合到一个配置中**。在 JavaConfig 形式的配置中，则使用 @Import 这个 Annotation 完成同样目的。

@Import 只负责引入 JavaConfig 形式定义的 IoC 容器配置，如果有一些遗留的配置或者遗留系统需要以 XML 形式来配置（比如 dubbo 框架），我们依然可以通过 @ImportResource 将它们一起合并到当前 JavaConfig 配置的容器中。

```java
@Configuration
@Import(MockConfiguration.class)
public class XConfiguration {
    ...
}
```



@**ModelAttribute**

​	修饰方法时表示该方法在当前controller的所有方法执行前执行。修饰方法参数时表示该参数的值源于model中的同名属性。



## Spring Boot核心功能

### 独立运行的Spring项目

​	Spring Boot 可以以 jar 包的形式独立运行，运行一个 Spring Boot 项目只需通过 java–jar xx.jar 来运行。

### 内嵌Servlet容器

​	Spring Boot 可选择内嵌 Tomcat、Jetty 或者 Undertow，这样我们无须以 war 包形式部署项目。

### 提供启动器starter简化Maven配置

​	Spring 提供了一系列的 starter pom 来简化 Maven 的依赖加载，例如，当你使用了spring-boot-starter-web 时，会自动加入所有与Javaweb相关的jar包。

### 自动配置Spring

​	Spring Boot 会根据在类路径中的 jar 包、类，为 jar 包里的类自动配置 Bean，这样会极大地减少我们要使用的配置。当然，Spring Boot 只是考虑了大多数的开发场景，并不是所有的场景，若在实际开发中我们需要自动配置 Bean，而 Spring Boot 没有提供支持，则可以自定义自动配置。

### 准生产的应用监控

​	Spring boot 提供基于http,ssh,telnet对运行时的项目进行监控。

### 无代码生成和xml配置

​	spring boot 不是借助代码生成来实现的，而是通过注解来实现的，这是spring 4提供的新特性。Spring 4提倡使用Java配置和注解配置组合，而spring boot不需要任何xml配置即可实现spring的所有配置。



## Spring boot的优缺点

**优点**

快速构建项目

对主流开发框架的无配置集成

项目可以独立运行，无需外部依赖Servlet容器

提供运行时的应用监控

极大地提高了开发、部署效率

与云计算的天然集成



**缺点**

版本迭代速度很快，一些模块改动很大

由于不需要自己做配置，报错时很难定位

网上现成的解决方案比较少



## @SpringbootApplication

```java
@Target({ElementType.TYPE})//作用对象
@Retention(RetentionPolicy.RUNTIME)//作用时间
@Documented//被写入文档
@Inherited//可被继承
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
```

​	@SpringbootApplication里面包含了上述七个注解，其中前四个@Target、@Retention、@Document、@Inherited是四大元注解。后三个作用分别是：

​	@SpringbootConfiguration注解 主要是封装@Configuration注解，而@Configuration注解与@Bean注解结合实现bean的纯Java方式注入，其作用是表明当前类是一个bean的配置类。

​	@**EnableAutoConfiguration**注解 **启动自动配置导入选择器功能**，这是SpringBoot实现自动配置的核心。基于@Import注解，将所有的符合自动装配条件的bean注入IOC容器。其中最重要的是@Import（EnableAutoConfigurationImportSelector.class），借助EnableAutoConfigurationImportSelector这个类，可以将可以将所有符合条件的@Configuration配置加载到IOC容器中。

![image-20210709134451752](.\src\main\resources\img\springboot-01.jpg)



​	借助了Spring中的SpringFactoriesLoader。

​	SpringFactoriesLoader作用是获取jar包中的工厂类，然后为工厂类创建实例。

![img](.\src\main\resources\img\springFactoriesLoader-01.jpg)

![img](.\src\main\resources\img\springFactoriesLoader-02.jpg)

​	@ComponentScan注解 扫描包，注入包中的bean。



## SpringBoot执行流程

​	调用SpringApplication的run方法

​	创建IOC容器...



## 启动器

​	

### SpringBoot两大特点

1. 约定大于配置
2. 通过spring-boot-start-自动配置依赖模块。

启动器的原始依赖都在父项目中。

### spring-boot-starter-logging

​	导入此以来后可以直接使用log4j，log4j2，commons logging 等等多种日志。

### spring-boot-starter-web

​	使用 Spring MVC 构建 Web（包括 RESTful）应用程序。使用 Tomcat 作为默认的嵌入式容器。

### spring-boot-starter-jdbc

### spring-boot-starter-aop

### spring-boot-starter-security

### spring-boot-starter-actuator

# Spring Security

​	安全是软件设计中一个重要的部分。它确保了只有被授权的用户才能够访问对应的资源。其中**认证（authentication）和授权（authorization）**是至关重要的两个部分。



​	Spring Security框架不但囊括了基本的认证和授权功能，而且还提供了加密解密、统一登陆等一系列相关支持。





# 注解集

## 创建bean

1. **@Component**  可以使用此注解描述 Spring 中的 Bean，但它是一个泛化的概念，仅仅表示一个组件（Bean），并且可以作用在任何层次。使用时只需将该注解标注在相应类上即可，默认单例。
2. **@Scope("prototype")** 作用域。
3. **@Repository**  用于将数据访问层（DAO层）的类标识为 Spring 中的 Bean，其功能与 @Component 相同，默认单例。
4. **@Service**  通常作用在业务层（Service 层），用于将业务层的类标识为 Spring 中的 Bean，其功能与 @Component 相同，默认单例。
5. **@Controller**  通常作用在控制层（如 [Struts2](http://c.biancheng.net/struts2/) 的 Action），用于将控制层的类标识为 Spring 中的 Bean，其功能与 @Component 相同,只不过默认为多例模式。





## 注入bean

1. **@Configration**   表示该类的主要目的是作为 Bean 定义的来源。所有spring在xml中的配置都可以在标有@Configuration这个配置类中配置。
2. **@Bean**  向IOC容器中注册组件。与<bean/>作用一样，都是用于类的实例化，配置并初始化为Spring IOC容器里面的一个对象。在默认情况下是任何条件都会被注册。
3. @**Conditional**  有条件的注册组件，用在@Bean的前面，只有满足一定条件时才会注册。
4. **@ComponentScan**   将扫描**包**下的所有组件（bean），将其注入到IOC容器中。对应 XML 配置形式中的 <context：component-scan> 元素，用于配合一些元信息 Java Annotation，比如 @Component 和 @Repository 等，**将标注了这些元信息 Annotation 的 bean 定义类批量采集到 Spring 的 IoC 容器中。**我们可以通过 basePackages 等属性来细粒度地定制 @ComponentScan 自动扫描的范围，如果不指定，则默认 Spring 框架实现会从声明 @ComponentScan 所在类的 package 进行扫描。
5. **@Import **  将括号里面的**类**中定义的bean加载到IOC容器。只负责引入 JavaConfig 形式定义的 IoC 容器配置。
6. **@ImportResource** 将XML形式定义的bean加载到 JavaConfig 形式定义的 IoC 容器。





## bean实例获取

1. **@Qualifier** 与 @Autowired 注解配合使用，会将默认的按 Bean 类型装配修改为按 Bean 的实例名称装配，Bean 的实例名称由 @Qualifier 注解的参数指定。
2. **@Autowired**  用于对 Bean 的属性变量、属性的 Set 方法及构造函数进行标注，配合对应的注解处理器完成 Bean 的自动配置工作。**默认按照 Bean 的类型**进行装配。
3. **@Resource**  其作用与 Autowired 一样。其区别在于 @Autowired 默认按照 Bean 类型装配，而 @Resource 默认按照 Bean 实例名称进行装配。@Resource 中有两个重要属性：name 和 type。Spring 将 name 属性解析为 Bean 实例名称，type 属性解析为 Bean 实例类型。如果指定 name 属性，则按实例名称进行装配；如果指定 type 属性，则按 Bean 类型进行装配。如果都不指定，则**先按 Bean 实例名称装配，如果不能匹配，则再按照 Bean 类型进行装配**；如果都无法匹配，则抛出 NoSuchBeanDefinitionException 异常。

## SpringMVC注解

1. **@RequestMapping** Spring MVC 中使用 @RequestMapping 来映射请求，也就是通过它来指定控制器可以处理哪些URL请求。可被**@GetMapping、@PostMapping、@PutMapping、@DeleteMapping、@PatchMapping**注解替换，例如：@RequestMapping(value="/get/{id}",method=RequestMethod.GET)=@GetMapping("/get/{id}")。
2. **@ResponseBody**  将 controller的java对象返回值转为json格式的数据。用于ajax异步传输。
3. @**RequestBody** 
4. @**RequestParam** 可获取多个参数，POST,PUT中使用
5. **@RestController**  @Controller + @ResponseBody，主要是为了使 http 请求返回 json 或者xml格式数据，一般情况下都是使用这个注解。
6. 

## Spring Security注解

1. **@Valid** 用于对象属性字段的规则检测。
2. **@ModelAttribute** 修饰方法，表明该方法在当前Controller的所有响应方法前面执行。主要用来做一些权限校验等。



1. **@PropertySource 与 @PropertySources** @PropertySource **用于从某些地方加载 *.properties 文件内容，并将其中的属性加载到 IoC 容器**中，便于填充一些 bean 定义属性的占位符（placeholder）。使用 Java 8 或者更高版本开发，那么可以并行声明多个 @PropertySource。使用低于 Java 8 版本的 Java 开发 Spring 应用，又想声明多个 @PropertySource，则需要借助 @PropertySources 的帮助。
2. **@SpringBootApplication**  SpringBoot应用启动类的注解，它主要包含@Configuration、@EnableAutoConfiguration、@ComponentScan三大注解。其中@Configuration就是JavaConfig形式的IOC配置类。
3. @**EnableAutoConfiguration** 









# Hibernate

​	Hibernate是一种ORM（Object Relative DataBase-Mapping）框架，在Java对象和关系型数据库之间建立某种映射关系，以实现直接存取Java对象。是JPA的一种实现方式。

​	JPA是Java Persistence API的简称，即Java持久层API，是描述对象——关系表的映射关系，并将运行期的实体对象持久化到数据库中。

​	Spring的data jpa是一个JPA数据访问抽象。也就是说Spring Data JPA不是一个实现或者JPA提供的程序，它只是一个抽象层，主要应用于简化各种持久层存储实现数据访问层所需的样板代码。其底层使用Hibernate来实现。



# Redis



# Kafka







# MySQL

存储引擎

索引

主从复制

SQL执行流程









# Vue

## MVVM架构

​	M:Model，模板层，表示JavaScript对象数据。
​	V:View，视图层，表示DOM（HTML操作的元素)。
​	VM:ViewModel,连接视图数据的中间件，对视图数据进行双向绑定；Vue.js就是MVVM中的ViewModel层的实现者。
​	MVVM架构中，不允许视图数据直接通信，只能通过ViewModel来通信，而ViewModel就是定义了一个Observer观察者。
​	**ViewModel 能够观察到数据变化，并对视图对应的内容进行更新。**
​	**ViewModel 能够监听到视图的变化，并通知数据发生改变。**





## vue七大常用属性

### el: element

​	用来指示vue编译器从什么地方开始解析 vue的语法，可以说是一个占位符。相当于一个容器，跟上面的div id = "app"做关联，从此以后上面div id = "app"里面的内容要通过vue来渲染,都要经过vue处理才能看得到上面div里面的内容。

### data

​	用来组织从view中抽象出来的属性，可以说将视图的数据抽象出来存放在data中。

### template

​	用来设置模板，会替换页面元素，包括占位符。相当于html中的<body>

### methods

​	放置页面中的业务逻辑，js方法一般都放置在methods中，用来写方法。
computed和methods是有区别的：computed是在**值发生改变**的时候才会触发效果，而methods只要**刷新**执行了就会触发，所有平时写VUE的时候，能用computed的尽量使用

### render

​	创建真正的virtual Dom

### computed

​	根据已经存在的属性计算出新的属性，对于同样的数据，会缓存。当其依赖属性的**值发生变化**是，这个属性的值会自动更新，与之相关的DOM部份也会同步自动更新。其实一般情况，我也会把一些关于逻辑的代码都写在computed中。

### watch

```vue
watch:function(new,old){}
```
​	监听data中数据的变化,两个参数，一个返回新值，一个返回旧值.
​	当有一些数据需要**随着其它数据变动而变动**时或者**执行异步操作**或**开销较大操作**时，建议使用watch。



## Vue生命周期

![Vue 实例生命周期](.\src\main\resources\img\vue-lifecycle.jpg)





created: 实例创建之前执行的钩子。

beforeMounted: 编译好的HTML挂载到对应虚拟dom时触发的钩子。此时页面并没有内容。

mounted：编译好的HTML挂载到页面完成后执行的钩子，此钩子函数中一般会做一些ajax请求获取数据，进行数据初始化。mounted在整个实例中只执行一次。

beforeUpdate 更新之前执行的钩子。

VirtualDom 实时监控数据变化，并随之更新Dom。

updates: 更新之后执行的钩子。

beforeDestroed: 实例销毁之前 执行的钩子。

destroyed: 实例销毁完成时 执行的钩子。

## Axios

​	vue.js是一个视图层框架，严格遵循SOC原则（关注度分离原则），所以Vue.js并不包含AJAX的通信功能，而Axios完美的解决了通信问题。少用使用jQuery，因为它操作Dom过于频繁。Axios是一个开源的可以用在浏览器端和Node.js的异步通信框架。主要作用是实现AJAX异步通信。



### 功能特点

​	从浏览器中创建XMLHttpRequests。

​	从node.js创建http请求。  ？

​	支持Promise API。   ？

​	拦截请求和响应。//拦截器

​	转换请求数据和响应数据。//过滤器

​	取消请求。//拦截器

​	客户端支持防御XSRF。

​	



## vue-cli

​	vue-cli是官方提供的一个脚手架，用于快速生成一个vue项目的模板。类似天涯maven。

