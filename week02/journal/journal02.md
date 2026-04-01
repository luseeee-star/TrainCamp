# 学习笔记
## component和其它Mapper...关于配置类的区别
**根本区别**：自动发现 vs. 手动规则
Controller / Service / Mapper：
特权：Spring 为它们内置了全自动机制。
逻辑：只要扫描到注解，框架自动知道“这是控制器”或“这是服务”，直接建立连接或注册路由。
状态：入职即上岗。

拦截器 (Interceptor)：
限制：Spring MVC 无法自动猜测你的业务规则。
逻辑：框架不知道你想拦截哪些路径（/api/*?）、排除哪些（/login?）、以及执行顺序。这些信息必须通过代码显式配置。
状态：入库待安装（加了 @Component 只是入库，不写配置类永远不工作）。

# 日志使用快速参考

## 如何在新文件中添加日志

### 1. 注入Logger
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClass {
    private static final Logger log = LoggerFactory.getLogger(MyClass.class);
    
    // 或在Controller和Service中
    public static final Logger log = LoggerFactory.getLogger(MyClass.class);
}
```

### 2. 记录不同级别的日志

#### DEBUG - 详细的调试信息
```java
log.debug("查询用户: {}", userid);
log.debug("用户 {} 上传报修单图片: {}", userid, fileName);
```

#### INFO - 重要的业务操作
```java
log.info("用户 {} 登录成功", userid);
log.info("创建新报修单 - 用户: {}, 设备: {}", userid, device_type);
```

#### WARN - 业务异常或不符合预期
```java
log.warn("用户 {} 登录失败 - 账号或密码错误", userid);
log.warn("用户 {} 修改密码失败 - 原密码错误", userid);
```

#### ERROR - 系统异常（带堆栈跟踪）
```java
log.error("全局异常捕获 - 异常类型: {}, 错误信息: {}", e.getClass().getSimpleName(), e.getMessage(), e);
```

## 日志最佳实践

### ✅ 推荐做法

```java
// 1. 包含上下文信息
log.info("用户 {} 创建报修单 - 设备: {}, 宿舍: {}", userid, deviceType, dormInfo);

// 2. 使用{}占位符（避免字符串拼接）
log.info("用户 {} 执行操作", userid);  // ✅ 好
log.info("用户 " + userid + " 执行操作");  // ❌ 差

// 3. 异常记录时带上堆栈跟踪
log.error("异常信息: {}", e.getMessage(), e);

// 4. 敏感操作使用INFO级别
log.info("用户 {} 删除报修单 - ID: {}", userid, formId);

// 5. 调试信息使用DEBUG级别
log.debug("方法参数: userid={}, formId={}", userid, formId);
```

### ❌ 避免的做法

```java
// 不要使用System.out.println
System.out.println("用户登录");  // ❌

// 不要过度日志输出关键性能路径
log.info("处理每个字节: " + byte);  // ❌ 会导致日志文件膨胀

// 不要在日志中明文记录敏码
log.info("用户 {} 密码: {}", userid, password);  // ❌ 安全风险
```

## 日志查看

### 1. 开发环境 - 实时查看控制台
项目启动后，所有INFO和以上级别的日志会输出到控制台，带颜色高亮：
- 蓝色: DEBUG
- 绿色: INFO
- 黄色: WARN
- 红色: ERROR

### 2. 生产环境 - 查看日志文件
```bash
# 查看所有日志
tail -f logs/application.log

# 查看错误日志
tail -f logs/error.log

# 搜索特定用户的操作
grep "用户 31252201" logs/application.log

# 搜索异常
grep "ERROR" logs/error.log | head -20
```

### 3. 按日期查看日志
```bash
# 查看特定日期的日志
tail logs/application-2024-03-30.1.log

# 查看最近的错误
tail -20 logs/error-2024-03-30.1.log
```

## 日志配置说明

### logback.xml 中的关键设置

```xml
<!-- 项目代码日志级别 DEBUG，系统框架 INFO -->
<logger name="lsj.gdut.project02" level="DEBUG" additivity="false">
    <appender-ref ref="CONSOLE"/>    <!-- 输出到控制台 -->
    <appender-ref ref="FILE"/>       <!-- 输出到文件 -->
    <appender-ref ref="ERROR_FILE"/> <!-- 错误单独输出 -->
</logger>

<!-- 日志文件轮转策略 -->
<!-- 单个文件最大10MB，超过自动创建新文件 -->
<maxFileSize>10MB</maxFileSize>

<!-- 日志保留30天 -->
<maxHistory>30</maxHistory>
```

## 常见日志场景

### 用户认证
```java
// LoginInterceptor
log.info("用户 {} (角色: {}) 认证成功, 访问路径: {}", userid, role == 0 ? "学生" : "维修人员", uri);
log.warn("令牌验证失败 - 原因: {}, 访问路径: {}", e.getMessage(), uri);
```

### 数据库操作
```java
// Service层
@Transactional
public void InsertForm(...) {
    log.info("创建新报修单 - 用户: {}, 设备: {}, 宿舍: {}", userid, device_type, dorm_info);
    formMapper.InsertForm(form);
    log.debug("报修单插入数据库成功 - 用户: {}", userid);
}
```

### 业务验证
```java
// 验证失败
log.warn("注册失败 - 用户已存在: {}", userid);
log.warn("用户 {} 宿舍绑定失败 - 宿舍格式有误: {}", userid, dormInfo);

// 验证成功
log.info("用户 {} 宿舍绑定成功: {}", userid, dormInfo);
```

### 异常处理
```java
// GlobalExceptionHandler
@ExceptionHandler(Exception.class)
public ResultJson handleException(Exception e) {
    log.error("全局异常捕获 - 异常类型: {}, 错误信息: {}", 
              e.getClass().getSimpleName(), e.getMessage(), e);
    return ResultJson.error(...);
}
```

## 性能影响

- **DEBUG日志**：仅在开发环境启用，生产环境可关闭
- **文件写入**：使用异步Appender可提升性能（如需配置）
- **日志大小**：自动轮转策略防止日志文件膨胀
- **查询性能**：日志不影响应用程序的主要业务逻辑

## 故障排查示例

### 问题：用户登录失败，需要排查原因

```bash
# 1. 查看登录相关日志
grep "31252201" logs/application.log | grep -i "登录"

# 输出示例：
# [INFO] 用户 31252201 尝试登录
# [WARN] 用户 31252201 登录失败 - 账号或密码错误
```

### 问题：报修单创建后没有出现在列表中，需要调查

```bash
# 1. 查看创建日志
grep "31252201" logs/application.log | grep "创建新报修单"

# 2. 查看是否有异常
grep "ERROR" logs/error.log | grep "31252201"

# 3. 查看数据库操作日志
grep "报修单插入数据库" logs/application.log
```

---


## 跟着黑马做了关于token持久与过期、图片文件传输，全局异常

# 个人感悟
虽然这次任务比较重，但确实收获很大，对spring框架和前后端的认识更深入了，也体会到了ai的发展速度之快与功能之强大

