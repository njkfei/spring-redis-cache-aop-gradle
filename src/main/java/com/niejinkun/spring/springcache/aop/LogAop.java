package com.niejinkun.spring.springcache.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import com.niejinkun.spring.springcache.model.User;

@Aspect
@Component
@EnableAutoConfiguration
public class LogAop 
{
	@Around("execution(* com.niejinkun.spring.springcache.controller.UserController.getUser(..)) && args(user_id)")
	public void logStatics(ProceedingJoinPoint joinPoint,int user_id) throws Throwable{
		
		long start = System.currentTimeMillis();
		
		Object user = joinPoint.proceed();
		
		System.out.println("log user: " + user.toString());
		long end = System.currentTimeMillis();
		
		System.out.println("use time : "  + (end - start));
	}
}
