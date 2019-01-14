# 文档说明
 A、通用Mapper文档
    https://mapperhelper.github.io/docs/7.use330/
    
B、 多数据源
    在Spring Boot中配置多数据源&使用动态数据源：https://www.jianshu.com/p/79fd05269e79
    或者查看SpringBoot Druid的官网：https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
    推荐使用V1.1.1以上版本
C、前端参数签名怎么生成，建议使用 JWT
一个文章连接：https://www.jianshu.com/p/576dbf44b2ae

# 工具模块说明    
 1、hutool工具包模块
 > 一个Java基础工具类，对文件、流、加密解密、转码、正则、线程、XML等JDK方法进行封装，组成各种Util工具类，同时提供以下组件：
 > - hutool-aop JDK动态代理封装，提供非IOC下的切面支持
> - hutool-bloomFilter 布隆过滤，提供一些Hash算法的布隆过滤
> - hutool-cache 缓存
> - hutool-core 核心，包括Bean操作、日期、各种Util等
> - hutool-cron 定时任务模块，提供类Crontab表达式的定时任务
> - hutool-crypto 加密解密模块
> - hutool-db JDBC封装后的数据操作，基于ActiveRecord思想
> - hutool-dfa 基于DFA模型的多关键字查找
> - hutool-extra 扩展模块，对第三方封装（模板引擎、邮件、Servlet、二维码等）
> - hutool-http 基于HttpUrlConnection的Http客户端封装
> - hutool-log 自动识别日志实现的日志门面
> - hutool-script 脚本执行封装，例如Javascript
> - hutool-setting 功能更强大的Setting配置文件和Properties封装
> - hutool-system 系统参数调用封装（JVM信息等）
> - hutool-json JSON实现
> - hutool-captcha 图片验证码实现

2、FastJson


特征&提供
最佳实践的项目结构、配置文件、精简的POM（查看项目结构图）
统一响应结果封装及生成工具
统一异常处理
简单的接口签名认证
常用基础方法抽象封装
使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
使用FastJsonHttpMessageConverter，提高JSON序列化速度
集成MyBatis、通用Mapper插件、PageHelper分页插件，实现单表业务零SQL
提供代码生成器根据表名生成对应的Model、Mapper、MapperXML、Service、ServiceImpl、Controller等基础代码，
其中Controller模板默认提供POST和RESTful两套，根据需求在CodeGenerator.genController(tableName)方法中自己选择，
默认使用POST模板。代码模板可根据实际项目的需求来扩展，由于每个公司业务都不太一样，所以只提供了一些比较基础、通用的模板，
主要是提供一个思路来减少重复代码的编写，我在实际项目的使用中，其实根据公司业务的抽象编写了大量的模板。
另外，使用模板也有助于保持团队代码风格的统一


开发建议
表名，建议使用小写，多个单词使用下划线拼接
Model内成员变量建议与表字段数量对应，如需扩展成员变量（比如连表查询）建议创建DTO，否则需在扩展的成员变量上加@Transient注解，详情见通用Mapper插件文档说明
建议业务失败直接使用ServiceException("message")抛出，由统一异常处理器来封装业务失败的响应结果，比如throw new ServiceException("该手机号已被注册")，会直接被封装为{"code":400,"message":"该手机号已被注册"}返回，无需自己处理，尽情抛出
需要工具类的话建议先从apache-commons-*和guava中找，实在没有再造轮子或引入类库，尽量精简项目
开发规范建议遵循阿里巴巴Java开发手册（最新版下载)
建议在公司内部使用ShowDoc、SpringFox-Swagger2 、RAP等开源项目来编写、管理API文档  
