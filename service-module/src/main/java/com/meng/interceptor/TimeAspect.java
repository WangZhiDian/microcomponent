package com.meng.interceptor;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {

	@Before("execution(public * com.meng.controller.FilterOpController.*(..))")
	public Object handlerControllerMethod() throws Throwable {
		System.out.println("time aspect start");
		return new Object();
	}


}
