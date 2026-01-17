package com.apk.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransferAspect 
{
	private static final Logger log = LoggerFactory.getLogger(TransferAspect.class);
	
	@Pointcut("execution(*com.apk.demo.service.BankService.transfer(..)*)")
	public void transferPoint() {}
	
	@Before("transferPoint()")
	public void beforeTransaction()
	{
		log.info("Transaction Started!");
	}
	
	@AfterReturning("transferPoint()")
	public void afterTransaction()
	{
		log.info("Transaction Completed!");
	}
	
	@AfterThrowing(value = "transferPoint()", throwing = "ex")
	public void afterThrowing(Exception ex)
	{
		log.info("Transaction Failed : {}", ex.getMessage());
	}
	
	@Around("transferPoint()")
	public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable
	{
		// log Arguments
		Object[] args = joinPoint.getArgs();
		Long fromId = (Long)args[0];
		Long toId = (Long)args[1];
		Double amount = (Double)args[2];
		
		//log Execution Time
		long start = System.currentTimeMillis();
		log.info("fromId : {}, toId : {}, amount : {}",
				fromId, toId, amount);
		
		Object result = joinPoint.proceed();
		
		long end = System.currentTimeMillis();
		log.info("Execution Time : {}ms", (end-start));
		
		return result;
		
	}
}
