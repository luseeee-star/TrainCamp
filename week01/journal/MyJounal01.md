# 首先浅浅记录这周学习遇到的问题或是之前的遗漏点
## SqlSessionFactory、SqlSession、Mapper 三者关系
1. 一：读取 MyBatis 配置文件
2. 二：根据配置，创建出数据库会话工厂（SqlSessionFactory
3. 三：拿到工厂中的执行数据库操作的工具（SqlSession
   拿到工具后，通过mapper操作数据库

## sql查询语句返回结果区别
查单个对象 → 没数据 → 返回 null
查集合列表 → 没数据 → 返回 空集合 []，不是 null

## 为什么要用静态代码块？
只执行一次
自动执行
提前准备资源

## 动态sql
传参要整个参数
set标签用于更新
where标签用于匹配

## Time库获取时间
在 Java 8 的 java.time 库中，要获取格式为 yyyy-MM-dd-HH-mm 的当前时间字符串，核心步骤是：获取当前时间对象 -> 定义格式模板 -> 格式化输出。
以下是代码示例：
```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetTimeExample {
public static void main(String[] args) {
// 1. 获取当前本地日期和时间
LocalDateTime now = LocalDateTime.now();

        // 2. 定义格式模板
        // yyyy: 年
        // MM: 月 (注意必须大写)与分mm区分
        // dd: 日
        // HH: 时 (24小时制，如果是12小时制用 hh)
        // mm: 分 (注意必须小写)
        // 注意：你要求的格式中间是横杠 "-"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

        // 3. 格式化时间
        String formattedTime = now.format(formatter);

        // 输出结果，例如：2026-03-18-09-38
        System.out.println(formattedTime);
    }
}
```
## Spring的IOC DI AOP

---
1. IOC (Inversion of Control) - 控制反转
   核心概念： “控制反转”是一种设计原则，而不是具体的技术实现。它的核心思想是将对象的创建、初始化和依赖关系的管理权，从代码内部（由程序员手动 new 对象）转移给外部容器（在 Spring 中就是 IoC Container）。
- 传统方式： 类 A 需要使用类 B，类 A 内部直接 new B()。这导致类 A 和类 B 强耦合。
- IOC 方式： 类 A 只需要声明“我需要类 B”，具体的创建和赋值工作由 Spring 容器完成。类 A 不再控制依赖对象的生命周期。

---
2. DI (Dependency Injection) - 依赖注入
   核心概念：DI 是 IOC 的具体实现方式。 既然控制权交给了容器，那么容器如何把依赖对象交给需要的类呢？就是通过“注入”。
   容器在运行期间，动态地将某个对象所依赖的其他对象，“注入”到该对象中。
   常见的注入方式：
1. 构造器注入 (Constructor Injection)： 通过构造函数传递依赖对象（推荐，保证对象不可变且完全初始化）。
   ```java
   public class UserService {
   private final UserRepository userRepository;
   // 容器调用此构造函数注入 userRepository
   public UserService(UserRepository userRepository) {
   this.userRepository = userRepository;
   }
   }
   ```
1. Setter 注入 (Setter Injection)： 通过 Setter 方法传递依赖对象。
   ```java
   public class UserService {
   private UserRepository userRepository;
   public void setUserRepository(UserRepository userRepository) {
   this.userRepository = userRepository;
   }
   }
   ```
1. 字段注入 (Field Injection)： 直接使用 @Autowired 注解在字段上（开发便捷，但不推荐用于生产环境核心逻辑，因为不利于测试和不可变性）。
   ```java
   @Component
   public class UserService {
   @Autowired
   private UserRepository userRepository;
   }
   ```

---
3. AOP (Aspect-Oriented Programming) - 面向切面编程
   核心概念： AOP 旨在将那些与业务逻辑无关，却为多个对象共同使用的功能（如日志记录、事务管理、权限校验、性能监控等）抽取出来，形成“切面（Aspect）”。
   在没有 AOP 时，这些代码会散落在各个业务方法中，导致代码重复且难以维护（称为“代码横切关注点”）。AOP 允许你在不修改源代码的情况下，将这些功能动态地织入（Weaving）到指定的方法执行前后。
   关键术语：
- Aspect (切面)： 横切关注点的模块化（例如：一个日志模块）。
- Join Point (连接点)： 程序执行过程中的某个点（通常是方法执行）。
- Pointcut (切入点)： 定义哪些连接点会被切面拦截（例如：所有以 save 开头的方法）。
- Advice (通知/增强)： 切面在特定连接点执行的动作。
    - @Before: 方法执行前。
    - @After: 方法执行后（无论是否异常）。
    - @AfterReturning: 方法正常返回后。
    - @AfterThrowing: 方法抛出异常后。
    - @Around: 环绕通知，最强大，可以控制方法是否执行。
      代码示例（声明一个日志切面）：
      ```java
      @Aspect
      @Component
      public class LogAspect {

      // 定义切入点：com.example.service 包下所有方法
      @Pointcut("execution(* com.example.service.*.*(..))")
      public void serviceLayer() {}

      // 前置通知：在方法执行前打印日志
      @Before("serviceLayer()")
      public void logBefore(JoinPoint joinPoint) {
      System.out.println("正在执行方法: " + joinPoint.getSignature().getName());
      }

      // 环绕通知：计算方法执行时间
      @Around("serviceLayer()")
      public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
      long start = System.currentTimeMillis();
      Object result = joinPoint.proceed(); // 执行原方法
      long end = System.currentTimeMillis();
      System.out.println("方法耗时: " + (end - start) + "ms");
      return result;
      }
      }
      ```
      
# 其次是个人心得体会
其实一开始看到这个任务怕做不完，但是周一周二周三挤通过时间竟然奇迹般地做完了。
我觉得可能与寒假的时候就提前学完了mybatis所以上手很快有关系，于是我提前预习了springboot和restful。
虽然这次开完会还是觉得任务有点重，但我觉得只要不放弃向前冲就能解决困难。