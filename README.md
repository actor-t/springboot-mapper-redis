# 前沿
简介：springboot-mapper-redis是一个基于Spring Boot & MyBatis & redis的maven种子系统，用于快速构建中小型API、RESTful API项目。  
主要版本：springboot采用2.x版本、mybatis3.4X版本、通用mapper使用4.X版本

# 文档说明
1、集成MyBatis、通用Mapper插件、PageHelper分页插件，实现单表业务零SQL，可以说学会使用这套架构将节约你百分之五十的开发时间！  
2、集成Druid数据库连接池与监控，默认用户名admin、密码123456，用于监控业务系统的sql使用情况等  
3、使用FastJsonHttpMessageConverter，提高JSON序列化速度，用于redis对象的转换等  
4、提供基础方法基础服务的封装，对于单表的增删查改，包括多条件查询，分页查询都已经封装好，根据代码生成器生成对应的Model、Mapper、  
   MapperXML、Service、ServiceImpl、Controller等基础代码，另外，使用模板也有助于保持团队代码风格的统一  
5、统一响应结果封装及生成工具、统一异常处理、简单的接口签名认证   
6、使用redis作为系统的缓存架构  
7、拦截器、过滤器、监听器等实现跨域、签名、token认证等  
8、websocket的使用事例  
9、定时器scheduling的使用  
10、系统日志的记录，可根据日志级别，打印sql日志，用于快速线上定位问题  
11、引入HuTool作为通用工具包  
12、阿里云短信工具  
# 码上开始
1、clone项目  
2、修改coreconstant里的常量  
3、修改日志目录pom文件  
4、修改你的项目名和常量名一样  
5、尽情享用.......  

# 参考技术文档
[通用Mapper文档](https://mapperhelper.github.io/docs/7.use330/)  
[MyBatis查看官方中文文档 ](http://www.mybatis.org/mybatis-3/zh/index.html)  
[MyBatis 分页插件 PageHelper](https://pagehelper.github.io/)  
[Druid Spring Boot Starter](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter/)  
[Fastjson](https://github.com/Alibaba/fastjson/wiki/%E9%A6%96%E9%A1%B5)  
[前端参数签名怎么生成，建议使用 JWT](https://www.jianshu.com/p/576dbf44b2ae)  
[Hutool](http://hutool.mydoc.io/)  
[通用mapper更新地址]( https://github.com/abel533/Mapper/wiki/changelog)  

# 开发建议
1、表名，建议使用小写，多个单词使用下划线拼接  
2、Model内成员变量建议与表字段数量对应，如需扩展成员变量（比如连表查询）建议创建DTO，否则需在扩展的成员变量上加@Transient注解，详情见通用Mapper插件文档说明  
3、建议业务失败直接使用ServiceException("message")抛出，由统一异常处理器来封装业务失败的响应结果，比如throw new ServiceException("该手机号已被注册")，会直接被封装为{"code":400,"message":"该手机号已被注册"}返回，无需自己处理，尽情抛出  
3、需要工具类的话建议先从apache-commons-*、hutool和guava中找，实在没有再造轮子或引入类库，尽量精简项目  
4、开发规范建议遵循阿里巴巴Java开发手册（最新版下载)  
5、建议使用的eclipse里的api插件工具，生成统一风格文档，保证代码的规范性  

# 特别感谢
框架是站在“巨人”的肩膀上诞生的，特别感谢通用mapper的原作者（@土豆lihengming），框架最初很多借鉴了他的种子框架,
尤其是代码生成部分，自己只在其基础上做了些许修改，而其开源的通用mapper更是大大节约了我这种低端码农的开发工作量，
有更多的时间约妹打球啦哈哈哈哈。  

#温馨提示
欢迎广大码农clone使用、水平有限，框架中如有不足还望轻喷，同时也欢迎大家积极贡献，打造一个更加完美的开源微服务框架！  
[项目地址](https://github.com/actor-t/springboot-mapper-redis)  
如有疑虑欢迎咨询qq：2411559022  

### 如果您觉得可以，来一支1916抽抽哈哈  
<div align=center><img width="400" height="400" src="https://github.com/actor-t/springboot-mapper-redis/blob/master/src/test/java/s/%E5%BE%AE%E4%BF%A1.jpg"/>
<img width="400" height="400" src="https://github.com/actor-t/springboot-mapper-redis/blob/master/src/test/java/s/%E6%94%AF%E4%BB%98%E5%AE%9D.jpg"/></div>
