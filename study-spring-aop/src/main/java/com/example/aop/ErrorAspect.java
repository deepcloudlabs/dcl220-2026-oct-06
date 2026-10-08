package com.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Component
@Aspect
public class ErrorAspect {
	@AfterThrowing(pointcut = "execution(* *.*(..))", throwing = "e")
	public void logError(JoinPoint jp, Throwable e) throws Throwable {
		System.err.println(String.format("%s fails with reason %s", jp.getSignature().getName(), e.getMessage()));
	}
}
