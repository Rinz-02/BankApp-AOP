package com.apk.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect 
{
	@Before("execution(*com.apk.demo.service.BankService(..))*")
	public void isAuth()
	{
		boolean isAuth = true;
		if(!isAuth)
		{
			throw new RuntimeException("UnAuth");
		}
	}
}
