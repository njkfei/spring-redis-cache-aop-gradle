# spring-redis-cache-aop-gradle
 spring aop功能储存，全部基于java config。没有难看的XML配置文件。
 
## AOP集成方法
### 引入依赖
```
  compile("org.springframework:spring-aop:4.1.5.RELEASE")
	compile("org.aspectj:aspectjrt:1.6.11")
	compile("org.aspectj:aspectjweaver:1.6.11")
	compile("aopalliance:aopalliance:1.0")
```

### 加入配置
```
@EnableAutoConfiguration
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) // 这一行必须要有
public class AppConfig {

}
```

###　注入目标
```
@Aspect
@Component
@EnableAutoConfiguration
public class LogAop 
{
	@Before("execution(* com.niejinkun.spring.springcache.controller.UserController.getUser(..)) && args(user_id)")
	public void logBefore(int user_id) throws Throwable{
		
		System.out.println("Before log user: " + user_id);
	}
	
	@Around("execution(* com.niejinkun.spring.springcache.controller.UserController.getUser(..)) && args(user_id)")
	public void logStatics(ProceedingJoinPoint joinPoint,int user_id) throws Throwable{
		long start = System.currentTimeMillis();
		Object user = joinPoint.proceed();
		System.out.println("log user: " + user.toString());
		long end = System.currentTimeMillis();
		System.out.println("use time : "  + (end - start));
	}
	
	@After("execution(* com.niejinkun.spring.springcache.controller.UserController.getUser(..)) && args(user_id)")
	public void logEnd(int user_id) throws Throwable{
		System.out.println("After log user: " + user_id);

	}
}
```

说明如下：
* && args(user_id) 这个是目标函数的参数，只需要参数名，不需要参数类型，参数类型在后面指定
* (ProceedingJoinPoint joinPoint,int user_id)　如果有目标函数的参数，则需要在joinPoint后面加入参数类型和参数
* 	Object user 使用User user不起作用，不知道为什么。可能是User是特殊的类，和系统类冲突
*	@Before 
* @Around 
* @After
* before after无joinPoint参数
 
## 测试运行
由于本demo使用了cache,因此，对于相同的请求，如果目标没有被缓存命中，则AOP只能响应一次。否则，AOP不起作用。
