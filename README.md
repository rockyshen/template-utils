## 说明
这是我收集日常开发过程中抽象出来的复用逻辑，可以直接拷贝、也可以install之后作为依赖引入。

# result
## 【主要用这个】Result
统一返回结果类，V2版，基于@鱼皮的用户中心项目提取，更加简单明了。
- 1、定义Result类，包含code、msg、data、description属性，及各种构造函数
- 2、ResultUtils类，包含ok和fail静态方法，return new Result()，运用各种Result类的构造函数
- 3、StatusCodeEnum枚举类，包含code、msg、description属性，全参构造函数，getter方法、利用构造函数构造各种枚举状态
- 4、自定义异常类：BusinessException extends RuntimeException，定义属性：code、description、其中msg给父类，各种构造函数
- 5、全局异常处理类：GlobalExceptionHandler,利用AOP，拦截BusinessException：1、日志记录；2、return ResultUtils.fail()

# 自定义注解
## @NoLogAnnotation
- 本注解可以加到：参数上、方法上;
- 加到参数上：表示这个参数不允许记录到日志中，针对敏感信息脱敏：密码
- 加到方法上，表示这个方法的返回值不允许记录到日志中

# redis
1、整合Lua脚本，保证原子性

# 自定义Aop拦截器
## ControllerLogAspect
* 在所有controller请求前后环绕拦截
* 请求前记录：请求类.方法名、请求方法、请求参数
* 请求后记录：返回值
* 可以搭配@NoLogAnnotation，指定哪些参数记录，哪些参数不记录！

# API签名认证工具
genSign()方法实现，传入SecretKey，利用单向加密算法，生成sign字段。传递这个sign字段。
永远不要在请求中明文传递密码或secretKey
服务端利用存在服务端的secretKey，利用相同的单向加密算法，生成字符串，和sign字段对比
相同，则表示secretKey正确，不同则不正确！

# 自定义序列化器
利用ByteArrayOutputStream包装流 + ObjectOutputStream节点流，将JVM程序中的对象序列化成字节，暂存到内存中；
再利用ByteArrayInputStream包装流 + ObjectInputStream节点流，从内存中读取字节数据，反序列化为JVM中的具体对象！

# server和client
## 基于vertx
基于vertx实现Http协议、TCP协议的，服务端监听端口，客户端向服务端发送请求，服务端响应的完整过程！

# 工具类
## FileUtil
### collectToMap
基于根文件夹，收集根文件夹下所有的文件，收集成Map
如下示例
```
{
	file1.txt=file1.txt,
	file2.txt=file2.txt, 

	subFolderB={
		b2.txt=b2.txt, 
		b1.txt=b1.txt
	}, 
	 
	subFolderA={
		a1.txt=a1.txt, 
		a2.txt=a2.txt
	}, 
}
```

### reverseFromMap
基于上一个方法提供的Map，反推成一棵文件树形结构

### collectToList
基于根文件夹，无视层级结构，收集成一个List

### flatCopy
扁平化复制文件：基于一个根路径，提取里面的文件，不保留层级结构，将所有文件复制到目标路径下

## DownloadUtil
### downloadFileToDir
提供一个http下载链接，获取下载到的文件，并存放到提供的路径中

## ExcelUtil
### processExcel
按行读取参数，处理结果，回填结果的方法。