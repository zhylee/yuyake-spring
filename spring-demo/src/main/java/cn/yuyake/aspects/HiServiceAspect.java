package cn.yuyake.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(2)
@Component
public class HiServiceAspect {

	@Pointcut("execution(* cn.yuyake.service.impl.HiServiceImpl.*(..))")
	public void plugin() {
	}

	@Before("plugin()")
	public void before(JoinPoint joinPoint) {
		System.out.println("进行 before 拦截 " + joinPoint);
	}

	@After("plugin()")
	public void after(JoinPoint joinPoint) {
		System.out.println("进行 after 拦截 " + joinPoint);
	}

	@AfterReturning(pointcut = "plugin()", returning = "returnValue")
	public void afterReturning(JoinPoint joinPoint, Object returnValue) {
		System.out.println("进行 return 拦截 " + joinPoint + "，返回值[" + returnValue + "]");
	}
}
