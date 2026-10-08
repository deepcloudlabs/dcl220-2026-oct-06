package com.example.aop;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Component
@Aspect
public class ProfileAspect {
	private static Logger logger = Logger.getLogger(ProfileAspect.class.getName());

	@Around("@annotation(profile)")
	public Object profileMethod(ProceedingJoinPoint pjp, Profile profile) throws Throwable {
		return profile(pjp, profile);
	}

	@Around("@target(profile)")
	public Object profileAllClassMethods(ProceedingJoinPoint pjp, Profile profile) throws Throwable {
		return profile(pjp, profile);
	}

	public Object profile(ProceedingJoinPoint pjp, Profile profile) throws Throwable {
		String methodName = pjp.getSignature().getName();
		long start = System.nanoTime();
		Object result = pjp.proceed();
		long stop = System.nanoTime();
		long duration = stop - start;
		long convertedDuration = profile.value().convert(duration, TimeUnit.NANOSECONDS);
		logger.info(
				String.format("%s runs %d %s.", methodName, convertedDuration, profile.value().name().toLowerCase()));
		return result;
	}
}
