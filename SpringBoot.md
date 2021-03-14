# SpringBoot入门

## 简介

SpringBoot 是一个快速开发的框架, 封装了Maven常用依赖、能够快速的整合第三方框架；简化XML配置，全部采用注解形式，内置Tomcat、Jetty、Undertow，帮助开发者能够实现快速开发，SpringBoot的Web组件 默认集成的是SpringMVC框架。

**SpringBoot原理**

1. 能够帮助开发者实现快速整合第三方框架 （原理：Maven依赖封装）
2. 去除xml配置 完全采用注解化 （原理：Spring体系中内置注解方式）
3. 无需外部Tomcat、内部实现服务器（原理：Java语言支持内嵌入Tomcat服务器）



## 配置

### maven配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>LearnSpringBoot_ysj</artifactId>
    <version>1.0-SNAPSHOT</version>
  	<!-- 在pom.xml中引入spring-boot-start-parent,可以提供依赖管理，引入以后在申明其它dependency的时候就不需要version了。-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.8.RELEASE</version>
    </parent>
    <dependencies>
        <!-- Spring SpringMVC -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

可以看到添加spring-boot-starter-web一个依赖，SpringBoot就会帮我们引入很多依赖。



### 启动类

```java
package com.yaohl0911;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

结合源码，可以清晰地看到@SpringBootApplication是@SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan注解的集合。@ComponentScan默认的扫包范围是当前启动类所在目录及其子目录，所以一般建议启动类放在最外面。

@SpringBootConfiguration 配置类

@EnableAutoConfiguration 自动注入

@ComponentScan 扫包范围

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
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
    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    Class<?>[] exclude() default {};

    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    String[] excludeName() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "basePackages"
    )
    String[] scanBasePackages() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "basePackageClasses"
    )
    Class<?>[] scanBasePackageClasses() default {};
}
```



### 注解相关

**@RestController和@Controller注解的区别**

@RestController注解对应的类中所有SpringMVC url接口映射都是返回json格式
@Controller注解对应的类中所有SpringMVC url接口映射都是返回的跳转，要想返回json格式需要在每个方法上加上@ResponseBody注解

使用@RestController就不需要在每个方法上加@ResponseBody注解了，以下两种实现是等效的：

```java
package com.yaohl0911.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloService {
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
```

```java
package com.yaohl0911.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HiService {
    @RequestMapping("/hi")
    @ResponseBody
    public String Hi() {
        return "Hi";
    }
}
```



# SpingBoot Web相关

## YML和Properties

SpringBoot支持两种配置方式,一种是properties文件,一种是yml。使用yml可以减少配置文件的重复性。properties采用key = value的格式，yml采用缩紧的格式。在企业中yml方式用的是比较多的。

例如：application.properties配置

```properties
User.name = tom
User.age = 22
```

 例如：application.yml配置

```yml
User:
  name: tom
  age: 22
```

## 静态资源访问

在我们开发Web应用的时候，需要引用大量的js、css、图片等静态资源。

**默认配置**

Spring Boot默认提供静态资源目录位置需置于classpath下，目录名需符合如下规则：

/static

/public

/resources     

/META-INF/resources

举例：我们可以在src/main/resources/目录下创建static，在该位置放置一个图片文件。启动程序后，尝试访问http://localhost:8080/name.jpg。如能显示图片，配置成功。

 

**微服务项目**

前后端分离

前端----vue----前端工程师

后端---springboot--后端工程师

 

**动静分离**

静态资源主要放在cdn上，减少带宽距离传输 降低自己服务器带宽占用。



## Web页面渲染

**传统项目，非前后端分离**

com.yaohl0911.controller	 ---视图层 渲染我们页面

com.yaohl0911.service		  ---业务逻辑层

com.yaohl0911.dao				---数据库访问层



**前后端分离项目**

在之前的示例中，我们都是通过@RestController来处理请求，所以返回的内容为json对象。那么如果需要渲染html页面的时候，要如何实现呢？

模板引擎能够非常好的帮助seo搜索到该网页

在动态HTML实现上SpringBoot依然可以完美胜任，并且提供了多种模板引擎的默认配置支持，所以在推荐的模板引擎下，我们可以很快的上手开发动态网站。

SpringBoot提供了默认配置的模板引擎主要有以下几种：

- Thymeleaf
- FreeMarker
- Velocity
- Groovy
- Mustache

SpringBoot建议使用这些模板引擎，避免使用JSP。

当你使用上述模板引擎中的任何一个，它们默认的模板配置路径为：src/main/resources/templates。当然也可以修改这个路径，具体如何修改，可在后续各模板引擎的配置属性中查询并修改。

### Freemarker

配置文件，不配置，使用默认配置也可以

```
spring:
  http:
    encoding:
      force: true
      ## 模版引擎编码为UTF-8
      charset: UTF-8
  freemarker:
    allow-request-override: false
    cache: false
    check-template-location: true
    charset: UTF-8
    content-type: text/html; charset=utf-8
    expose-request-attributes: false
    expose-session-attributes: false
    expose-spring-macro-helpers: false
    ## 模版文件结尾.ftl
    suffix: .ftl
    ## 模版文件目录
    template-loader-path: classpath:/templates  # freemarker的模版路径，这里是默认值
```

java代码配合ftl文件一起看，注意String，int，list在ftl里分别是怎么处理的。

```java
package com.yaohl0911.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class FreemarkerIndexController {
    @RequestMapping("/freemarkerIndex")
    public String freemarkerIndex(Map<String, Object> result) {
        result.put("name", "jerry");
        result.put("sex", "0");
        result.put("age", 22);
        ArrayList<String> list = new ArrayList<>();
        list.add("yy");
        list.add("zz");
        list.add("dd");
        result.put("userList", list);
        return "freemarkerIndex";
    }
}

```

```xml
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8"/>
    <title></title>
</head>
<body>
${name}

<#if sex=='0'>  <#--这里在java里是String-->
    男
<#elseif sex=='1'>
    女
<#else>
    其他
</#if>

<#if age gt 17> <#--这里在java里是int-->
    已经成年啦
<#else>
    未成年
</#if>

<#list userList as user>
    ${user}
</#list>

</body>
</html>
```

### Thymeleaf

thymeleaf是一款用于渲染XML/XHTML/HTML5内容的模板引擎，类似JSP，Velocity，FreeMaker等，它也可以轻易的与SpringMVC等Web框架进行集成作为Web应用的模板引擎。

**Maven依赖**

```xml
<!--Spring SpringMVC  -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!--引入thymeleaf的依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

**配置文件**

```xml
## ThymeLeaf配置
spring:
  thymeleaf:
    #prefix：指定模板所在的目录
    prefix: classpath:/templates/
    #check-tempate-location: 检查模板路径是否存在
    check-template-location: true
    #cache: 是否开启缓存，开发模式下设置为false，避免修改模板时重启服务器；上线时设置为true，可以提高性能。
    cache: true
    suffix:  .html
    encoding: UTF-8
    mode: HTML5
```

entity

```java
package com.yaohl0911.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThymeleafEntity {
    private String userName;
    private Integer age;
}
```

controller

```java
package com.yaohl0911.controller;

import com.yaohl0911.entity.ThymeleafEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ThymeleafController {
    @RequestMapping("/thymeleaf")
    public String myThymeleaf(Map<String, Object> result) {
        result.put("user", new ThymeleafEntity("tom", 12));
        return "thymeleaf";
    }
}
```

thymeleaf.html

```html
<!DOCTYPE html>
<!--需要在HTML文件中加入以下语句： -->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Show User</title>
</head>
<body>
<table>
    姓名:<span th:text="${user.userName}"></span>
    年龄:<span th:text="${user.age}"></span>
</table>
</body>
</html>
```

# SpingBoot数据库相关

## JDBC Telmplate

很少用，了解即可

```java
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Boolean inserUser(String name, Integer age) {
        int update = jdbcTemplate.update("insert into users values(null,?,?);", name, age);
        return update > 0 ? true : false;
    }
}
```



## Mybatis

常用，一定要掌握

### Maven依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.8.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- springboot 整合mybatis -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.1.1</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.21</version>
    </dependency>
</dependencies>
```

### 配置文件

数据库配置application.yml

```xml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: root
    # driver-class-name: com.mysql.jdbc.Driver
```

### 代码实现

**Entity**

```java
package com.yaohl0911.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 由参构造函数和午餐构造函数要注意，要不然可能解析不了
@NoArgsConstructor  
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
```

**Mapper**

```java
package com.yaohl0911.mapper;

import com.yaohl0911.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

// SpringBoot一般使用注解的方式，所以这了Mybatis也没有配置xml文件，而是用的注解方式
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE name = #{name}")
    User findByName(@Param("name") String name);
    @Select("SELECT * FROM USERS WHERE id = #{id}")
    User findById(@Param("id") Integer id);
    @Insert("INSERT INTO users (name, age) VALUES (#{name}, #{age});")
    int insertUser(@Param("name") String name, @Param("age") Integer age);
}
```

**Service**

```java
package com.yaohl0911.service;

import com.yaohl0911.entity.User;
import com.yaohl0911.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/findByName")
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
    @RequestMapping("/findById")
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
  
    @RequestMapping("/insertUser")
    public boolean insertUser(String name, Integer age) {
        int cnt = userMapper.insertUser(name, age);
        return cnt == 1 ? true : false;
    }
}
```

**启动配置**

```java
package com.yaohl0911;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yaohl0911.mapper") // 定义Mybatis中Mapper的扫描路径,要注意
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```



测试链接

```url
http://localhost:8080/insertUser?name=yy&age=30
http://localhost:8080/findByName?name=yy
http://localhost:8080/findById?name=3
```

## 事务管理



# SprintBoot热部署框架

知识拓展，用处不太大

原理：类加载器

只适合于本地开发环境，不适合生产环境

**Maven依赖**

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <scope>runtime</scope>
  <optional>true</optional>
</dependency>
```

**IDEA配置**

![image-20210313235436773](/Users/yaohailiang/Library/Application%20Support/typora-user-images/image-20210313235436773.png)

![image-20210313235643614](/Users/yaohailiang/Library/Application%20Support/typora-user-images/image-20210313235643614.png)

# SpringBoot日志相关

一般可以使用如下方式进行日志配置，注意下面MyApplication.class就对应当前类，表明要使用日志的类。

```java
private static Logger log = logger.getLogger(MyApplication.class);
```

在不同类中都要使用日志功能时，就要在每个类中添加相同的变量，会过于冗余。所以可以采用logback和log4j来打印日志。

## logback

SpringBoot默认整合了logback日志功能，需要添加Lombok依赖，结合@Slf4j注解就能使用日志功能了。

logback.xml配置文件，放在src/main/resources/log路径下。注意是当前**项目**的log目录下，而不是当前**module**的目录下。

### **日志级别**

- **ALL**：最低等级的，用于打开所有日志记录。
- **TRACE**：designates finer-grained informational events than the DEBUG.Since:1.2.12，很低的日志级别，一般不会使用。
- **DEBUG**：指出细粒度信息事件对调试应用程序是非常有帮助的，主要用于开发过程中打印一些运行信息。
- **INFO**： 消息在粗粒度级别上突出强调应用程序的运行过程。打印一些你感兴趣的或者重要的信息，这个可以用于生产环境中输出程序运行的一些重要信息，但是不能滥用，避免打印过多的日志。
- **WARN**：表明会出现潜在错误的情形，有些信息不是错误信息，但是也要给程序员的一些提示
- **ERROR**：指出虽然发生错误事件，但仍然不影响系统的继续运行。打印错误和异常信息，如果不想输出太多的日志，可以使用这个级别。
- **FATAL**：指出每个严重的错误事件将会导致应用程序的退出。这个级别比较高了。重大错误，这种级别你可以直接停止程序了。
- **OFF**：最高等级的，用于关闭所有日志记录。

### **logback.xml配置**

```xml
<configuration>
    <!-- 本文主要输出日志为控制台日志，系统日志，sql日志，异常日志 -->
    <!-- %m输出的信息,%p日志级别,%t线程名,%d日期,%c类的全名,,,, -->

    <!-- 控制台输出 -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d %p (%file:%line\)- %m%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- 系统info级别日志 -->
    <!-- <File> 日志目录，没有时会自动创建 -->
    <!-- <rollingPolicy>日志策略，每天建立一个日志文件，或者当天日志文件超过64MB时-->
    <!-- encoder 日志编码及输出格式 -->
    <appender name="fileLog"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>log/file/fileLog.log</File>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>log/file/fileLog.log.%d.%i</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <!-- or whenever the file size reaches 64 MB -->
                <maxFileSize>64 MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>
                %d %p (%file:%line\)- %m%n
            </pattern>
            <charset>UTF-8</charset>
            <!-- 此处设置字符集 -->
        </encoder>
    </appender>

    <!-- sql日志 -->
    <appender name="sqlFile"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>log/sql/sqlFile.log</File>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>log/sql/sqlFile.log.%d.%i</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <!-- or whenever the file size reaches 64 MB -->
                <maxFileSize>64 MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <!-- 对记录事件进行格式化。负责两件事，一是把日志信息转换成字节数组，二是把字节数组写入到输出流。-->
        <encoder>
            <!-- 用来设置日志的输入格式 -->
            <pattern>
                %d %p (%file:%line\)- %m%n
            </pattern>
            <charset>UTF-8</charset>
            <!-- 此处设置字符集 -->
        </encoder>
    </appender>

    <!-- 异常日志 -->
    <appender name="errorFile"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>log/error/errorFile.log</File>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>log/error/errorFile.%d.log.%i</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <!-- or whenever the file size reaches 64 MB -->
                <maxFileSize>64 MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <!-- 对记录事件进行格式化。负责两件事，一是把日志信息转换成字节数组，二是把字节数组写入到输出流。-->
        <encoder>
            <!-- 用来设置日志的输入格式 -->
            <pattern>
                %d %p (%file:%line\)- %m%n
            </pattern>
            <charset>UTF-8</charset>
            <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志都在这里 过滤出 error
             使用 try {} catch (Exception e){} 的话异常无法写入日志，可以在catch里用logger.error()方法手动写入日志
        -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 日志输出级别 -->
    <!-- All\DEBUG\INFO\WARN\ERROR\FATAL\OFF-->
    <!-- 打印info级别日志，分别在控制台，fileLog，errorFile输出，异常日志在上面由过滤器过滤出ERROR日志打印 -->
    <root level="INFO">
        <appender-ref ref="fileLog" />
        <appender-ref ref="console" />
        <appender-ref ref="errorFile" />
    </root>

    <!-- 打印sql至sqlFile文件日志 -->
    <logger name="com.dolphin.mapper" level="DEBUG" additivity="false">
        <appender-ref ref="console" />
        <appender-ref ref="sqlFile" />
    </logger>
</configuration>
```

### **SpringBoot属性配置**

SpringBoot工程配置logback配置文件的路径，在properties.yml文件中添加

```yml
logging:
  config: classpath:log/logback.xml
```

### 使用方法

加@Slf4j注解，在代码中使用log.xxx打印日志即可。

## log4j

### **日志级别**

机制：如果一条日志信息的级别大于等于配置文件的级别，就记录。

- trace：追踪，就是程序推进一下，可以写个trace输出
- debug：调试，一般作为最低级别，trace基本不用。
- info：输出重要的信息，使用较多
- warn：警告，有些信息不是错误信息，但也要给程序员一些提示。
- error：错误信息。用的也很多。
- fatal：致命错误。

输出源

- CONSOLE（输出到控制台）
- FILE（输出到文件）

格式

- SimpleLayout：以简单的形式显示
- HTMLLayout：以HTML表格显示
- PatternLayout：自定义形式显示

### Maven依赖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
<!-- spring boot start -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <!-- 排除自带的logback依赖 -->
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!-- springboot-log4j -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j</artifactId>
    <version>1.3.8.RELEASE</version>
</dependency>

```

### log4j.properties

```properties
#log4j.rootLogger=CONSOLE,info,error,DEBUG
log4j.rootLogger=DEBUG,error,CONSOLE,info
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n

log4j.logger.info=info
log4j.appender.info=org.apache.log4j.DailyRollingFileAppender
log4j.appender.info.layout=org.apache.log4j.PatternLayout
log4j.appender.info.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n
log4j.appender.info.datePattern='.'yyyy-MM-dd
log4j.appender.info.Threshold = info
log4j.appender.info.append=true
log4j.appender.info.File=SpringBoot03-db/src/main/resources/log/logfile/info.log

log4j.logger.error=error
log4j.appender.error=org.apache.log4j.DailyRollingFileAppender
log4j.appender.error.layout=org.apache.log4j.PatternLayout
log4j.appender.error.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n
log4j.appender.error.datePattern='.'yyyy-MM-dd
log4j.appender.error.Threshold = error
log4j.appender.error.append=true
log4j.appender.error.File=SpringBoot03-db/src/main/resources/log/logfile/error.log

log4j.logger.DEBUG=DEBUG
log4j.appender.DEBUG=org.apache.log4j.DailyRollingFileAppender
log4j.appender.DEBUG.layout=org.apache.log4j.PatternLayout
log4j.appender.DEBUG.layout.ConversionPattern=%d{yyyy-MM-dd-HH-mm} [%t] [%c] [%p] - %m%n
log4j.appender.DEBUG.datePattern='.'yyyy-MM-dd
log4j.appender.DEBUG.Threshold = DEBUG
log4j.appender.DEBUG.append=true
log4j.appender.DEBUG.File=SpringBoot03-db/src/main/resources/log/logfile/debug.log
```

### SpringBoots属性配置

```yml
logging:
  config: classpath:log4j.properties
```



### 使用方法

加@Log4j注解，在代码中使用log.xxx打印日志即可。



## AOP实现日志

在实际开发中常使用AOP实现日志功能。

### Maven依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### AOP配置

```java
package com.yaohl0911.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j  // 这里复用了logback的日志实现，也可以用log4j实现
public class LogAspect {
    // 切入点
    // com.yaohl0911.service.*表示com.yaohl0911.service下所有包
    // 最后一个*表示所有方法，(..)表示方法的所有参数
    @Pointcut("execution(public * com.yaohl0911.service.*.*(..))")
    public void serviceLog() {
    }

    // 前置拦截 请求方法之前做拦截
    @Before("serviceLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            log.info("name:{}, value:{}", name, request.getParameter(name));
        }
    }

    // 响应信息 目标方法请求之后的log打印
    @AfterReturning(returning = "ret", pointcut = "serviceLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
    }
}
```

如果使用logback或者log4j的日志实现，需要对应的配置文件，见前两节内容。



# SpringBoot配置相关

## 配置文件优先级

1. 在springboot整合配置文件，分成两大类：

   application.properties

   application.yml

   或者是

   Bootstrap.properties

   Bootstrap.yml

   相对于来说yml文件格式写法更加精简，减少配置文件的冗余性。

2. 加载顺序：

   bootstrap.yml 先加载 application.yml后加载

   ==bootstrap.yml 用于应用程序上下文的引导阶段。==

   bootstrap.yml 由父Spring ApplicationContext加载。

3. 区别：

   bootstrap.yml 和 application.yml 都可以用来配置参数。

   bootstrap.yml 用来程序引导时执行，应用于更加早期配置信息读取。可以理解成系统级别的一些参数配置，这些参数一般是不会变动的。一旦bootStrap.yml 被加载，则内容不会被覆盖。

   application.yml 可以用来定义应用级别的，应用程序特有配置信息，可以用来配置后续各个模块中需使用的公共参数等。



Properties在线转换yml格式网址：https://www.toyaml.com/index.html



## @ConfigurationProperties注解

好像用处不是很大，了解。

用@value注解去写的话代码还是比较多。

**Maven依赖**

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-configuration-processor</artifactId>
</dependency>
```

可以实现高亮和跳转

**用法格式**

```java

```



# SpringBoot定时任务

## @Scheduled定时任务

Spring提供的定时任务注解，但是不支持集群，所以在分布式领域很少用。

注意要用该定时任务的话，需要在启动类加上@EnableScheduling注解

每隔五秒执行一次的用法：

`@Scheduled(fixedRate = 5000)`	

如果要实现复杂的定时规则，也可以使用cron表达式

cron表达式生成网站：https://www.bejson.com/othertools/cron/

``@Scheduled(cron = “1/2 * * * * ?”)``



# SpringBoot的异步处理

启动类加@EnableAsync注解开启异步处理支持，需要执行异步方法上加 @Async

## 异步应用场景

@Async实际就是多线程封装的

异步线程执行方法有可能会非常消耗cpu的资源，所以大的项目建议使用MQ异步实现。

**应用场景举例**

注册场景，客户注册时需要完成两个步骤：1.出入数据；2.发送短信

如果是同步的方式完成，需要先插入数据，再发送短信，耗时4s，如果发送短信时间更长，客户需要长时间等待。

![image-20210314163105137](/Users/yaohailiang/Documents/Notes/image-20210314163105137.png)

如果采用异步的处理方式，在插入数据成功后就开启异步线程实现发短信的功能，客户等待时间只是插入数据的时间，客户不需要等待很长时间。

![image-20210314163348597](/Users/yaohailiang/Documents/Notes/image-20210314163348597.png)

## 实现举例

代码实现

```java
package com.yaohl0911.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterService {
    // 单线程实现，执行等待时间较长
//    @RequestMapping("/userRegister")
//    public boolean userRegister() throws InterruptedException {
//        addUser();
//        sms();
//        return true;
//    }

    // 手动新建线程实现，不推荐
//    @RequestMapping("/userRegister")
//    public boolean userRegister() throws InterruptedException {
//        addUser();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    sms();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    // 使用异步注解实现，在需要异步处理的实现（本例是sms()）上加@Async注解，同时在启动类开启异步注解支持
    // 这时候可能发现异步注解没有生效
    @RequestMapping("/userRegister")
    public boolean userRegister() throws InterruptedException {
        addUser();
        sms();
        return true;
    }

    public boolean addUser() throws InterruptedException {
        Thread.sleep(1000);
        return true;
    }
    @Async
    public boolean sms() throws InterruptedException {
        Thread.sleep(3000);
        return true;
    }
}
```

启动类

```java
package com.yaohl0911;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  // 开启异步注解支持
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 异步注解失效问题

注意：像上面这样实现，异步注解是不会生效的。如果异步注解是当前自己类，有可能AOP会失效，无法拦截注解，最终导致异步注解失效，需要经过代理类调用接口；

所以需要将异步的代码单独抽取成一个类调用接口。

下面代码通过把需要异步注解的服务拆分出来并单独注解的方式解决异步注解失效的问题，同时整合了线程池的功能。

注册服务代码

```java
package com.yaohl0911.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegisterService {
    @Autowired
    private SmsServiceAsync smsServiceAsync;

    @RequestMapping("/userRegister")
    public boolean userRegister() {
        addUser();
        smsServiceAsync.smsAsync();
        return true;
    }

    public boolean addUser() {
        log.info(">01<");
        try {
            log.info(">插入数据..<");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(">04<");
        return true;
    }
}
```

用@Asyn注解的短信服务，整合了线程池

```java
package com.yaohl0911.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsServiceAsync {
    @Async("taskExecutor")
    public String smsAsync() {
        log.info(">02<");
        try {
            log.info(">正在发送短信..<");
            Thread.sleep(3000);
        } catch (Exception e) {

        }
        log.info(">03<");
        return "短信发送完成!";
    }
}
```

线程池

```java
package com.yaohl0911.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * 每秒需要多少个线程处理?
     * tasks/(1/taskcost)
     */
    private int corePoolSize = 3;

    /**
     * 线程池维护线程的最大数量
     * (max(tasks)- queueCapacity)/(1/taskcost)
     */
    private int maxPoolSize = 3;

    /**
     * 缓存队列
     * (coreSizePool/taskcost)*responsetime
     */
    private int queueCapacity = 10;

    /**
     * 允许的空闲时间
     * 默认为60
     */
    private int keepAlive = 100;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 设置队列容量
        executor.setQueueCapacity(queueCapacity);
        // 设置允许的空闲时间（秒）
        //executor.setKeepAliveSeconds(keepAlive);
        // 设置默认线程名称
        executor.setThreadNamePrefix("thread-");
        // 设置拒绝策略rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
```

启动类

```java
package com.yaohl0911;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  // 开启异步注解支持
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

注意添加日志相关的配置文件：logback.x和application.yml



# SpringBoot异常相关

@ExceptionHandler 表示拦截异常

@ControllerAdvice 是 controller 的一个辅助类，最常用的就是作为全局异常处理的切面类

@ControllerAdvice 可以指定扫描范围

@ControllerAdvice 约定了几种可行的返回值，如果是直接返回 model 类的话，需要使用 @ResponseBody 进行 json 转换

* 返回 String，表示跳到某个 view
* 返回 modelAndView
* 返回 model + @ResponseBody

全局补货异常并翻译成用户易于理解的信息的类

```java
@ControllerAdvice
public class MayiktExceptionHandler {
    /**
     * 拦截运行异常出现的错误
     *
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<Object, Object> exceptionHandler() {
        Map<Object, Object> map = new HashMap<>();
        map.put("error", "500");
        map.put("msg", "系统出现错误");
        return map;
    }
}
```



# SpringBoot打包发布

使用mvn clean package 打包

使用java –jar + 包全路径名

如果没有主清单报错，在pom文件中新增以下内容指定主清单。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <mainClass>com.yaohl0911.Application</mainClass>
                <excludes>
                    <exclude>
                        <groupId>junit</groupId>
                        <artifactId>junit</artifactId>
                    </exclude>
                    <exclude>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-test</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

