## 说明
这是我收集日常开发中常用到的工具类，存放在此，下次需要直接从这里拷贝即可

每新加一个模版，就在这里进行说明，进行目录记录
#result
## Result类
统一返回结果类
- 1、Result类是封装Spring MVC的返回结果，包含经典四大件：code、message、data、datetime
- 2、ResultEnum枚举类是简单ok方法和fail方法的code和message
- 3、StatusCodeEnum枚举类，如果向Result.ok()/fail()中传递StatusCodeEnum，就会响应具体HTTP状态码

## 【主要用这个】Result_v2
统一返回结果类，V2版，基于@鱼皮的用户中心项目提取，更加简单明了。
- 1、定义Result类，包含code、msg、data、description属性，及各种构造函数
- 2、ResultUtils类，包含ok和fail静态方法，return new Result()，运用各种Result类的构造函数
- 3、StatusCodeEnum枚举类，包含code、msg、description属性，全参构造函数，getter方法、利用构造函数构造各种枚举状态
- 4、自定义异常类：BusinessException extends RuntimeException，定义属性：code、description、其中msg给父类，各种构造函数
- 5、全局异常处理类：GlobalExceptionHandler,利用AOP，拦截BusinessException：1、日志记录；2、return ResultUtils.fail()

# redis
## redis.conf
redis的配置文件，对一些常用参数添加注释，帮助理解整个配置文件；

## RedisUtil
封装Redisson库中对redis的操作，调用更直接。适合redis作为缓存
直接注入RedisUtil对象，基于redisUtil调用方法即可。
### 常用方法
#### 普通key-valeu
set、get、getExpire、expire、hasKey、del、setnx、incr、decr
#### Hash
hget、hmget、hset、hdel、hHasKey、hincr、hdecr
#### Set
sGet、sHasKey、sSet、sSetAndTime、sGetSetSize、setRemove、popMember
#### List
lGet、lGetIndex、lSet、lUpdateIndex、lRemove
#### Lua脚本
executeLua