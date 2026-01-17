package com.apk.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BankAspect 
{
	//to show where is the log coming from -> in this case BankAspect
	public static final Logger log = LoggerFactory.getLogger(BankAspect.class);
	
	//telling the Aspect when to run
	@Pointcut("execution(*com.apk.demo.service.BanksService.*(..))")
	public void bankMethod() {}
	 
	@Before("bankMethod()")
	public void beforeOperation()
	{
		log.info("Bank Operation Started");
	}
	
	@AfterReturning("bankMethod()")
	public void afterOperation()
	{
		log.info("Operation Completed!");
	}
	
	@AfterThrowing(value = "bankMethod()", throwing = "ex")
	public void afterThrowing(Exception ex)
	{
		log.error("Error Occured at BankService : {}", ex.getMessage());
	}
	
	@Around("bankMethod()")
	public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable
	{
		long start = System.currentTimeMillis();
		log.info("Method : {} ", joinPoint.getSignature().getName());
		
		Object result = joinPoint.proceed();
		
		long end = System.currentTimeMillis();
		log.info("Execution Time : {} ms", (end-start));
		
		return result;
		
	}
}
