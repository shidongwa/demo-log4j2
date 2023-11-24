## demo：log在 spring boot项目shutdown hook中不能工作

复现方式：
本项目spring boot 中logging.register-shutdown-hook参数配置为 true（默认值是 false），
即可复现问题。参数配置为 false 或者不配置时shutdown hook 中的日志会正常打印；
有的spring boot项目中logging.register-shutdown-hook参数配置为 false 或者不配置时日志也不能正常打印，
需要log4j2.xml 配置文件中加入shutdownHook="disable"才正常。原因不明，猜测项目中其他组件把 spring boot 
中这个参数的行为改变了。
正常打印日志参考下面。

1. spring boot 配置参数
```
   logging.register-shutdown-hook=true
```

2. log4j2配置文件
```
<Configuration status="INFO" shutdownHook="disable">
</Configuration>
```


**正常日志打印**
```
2023-11-24 21:48:16.651 [Thread-3] INFO  com.example.demolog4j2.service.MyService - Shutdown hook is running...
2023-11-24 21:48:16.652 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 1 / 10
2023-11-24 21:48:16.652 [SpringContextShutdownHook] INFO  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor - Shutting down ExecutorService 'applicationTaskExecutor'
2023-11-24 21:48:17.156 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 2 / 10
2023-11-24 21:48:17.660 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 3 / 10
2023-11-24 21:48:18.165 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 4 / 10
2023-11-24 21:48:18.669 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 5 / 10
2023-11-24 21:48:19.171 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 6 / 10
2023-11-24 21:48:19.675 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 7 / 10
2023-11-24 21:48:20.177 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 8 / 10
2023-11-24 21:48:20.680 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 9 / 10
2023-11-24 21:48:21.186 [Thread-3] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 10 / 10
Cleanup tasks completed.
```

**异常日志打印**

```
2023-11-24 21:55:46.433 [Thread-4] INFO  com.example.demolog4j2.service.MyService - Shutdown hook is running...
2023-11-24 21:55:46.435 [Thread-4] INFO  com.example.demolog4j2.service.MyService - shutdown progress: 1 / 10
Cleanup tasks completed.

Process finished with exit code 130 (interrupted by signal 2: SIGINT)
```