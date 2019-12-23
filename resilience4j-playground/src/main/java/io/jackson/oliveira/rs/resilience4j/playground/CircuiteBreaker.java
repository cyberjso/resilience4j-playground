package io.jackson.oliveira.rs.resilience4j.playground;

import java.io.IOException;
import java.time.Duration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

public class CircuiteBreaker {
	
	public String helloCircuit() {
	
		ApiCaller apiCaller = new ApiCaller();
		
		CircuitBreakerRegistry circuitBreakerRegistry = 
				  CircuitBreakerRegistry.ofDefaults();
		
		
		CircuitBreaker circuitBreaker = circuitBreakerRegistry
				  .circuitBreaker("circuiteBreakerExample");
		
		return  circuitBreaker.executeSupplier(() -> apiCaller.callExternalAPI("1"));		
	}
	
	public String recoverFromExeception() {
		CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("recoverableCircuit");

		CheckedFunction0<String> supplier =  CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> new ApiCaller().callForcingExceptin("1"));
		
		return Try.of(supplier).recover(throwable -> "This is the default string, when an exception is thrown").get();
	}
	
	
	public String buildCircuiteWithCustomConfigs( ) {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
			    .failureRateThreshold(50)
			    .waitDurationInOpenState(Duration.ofMillis(1000))
			    .permittedNumberOfCallsInHalfOpenState(2)
			    .slidingWindowSize(2)
			    .recordExceptions(RuntimeException.class)
			    .ignoreExceptions(IOException.class)
			    .build();
		
		CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);
		CircuitBreaker circuitBreaker = registry.circuitBreaker("circuiteBreakerExample");
		
		CheckedFunction0<String> supplier =  CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> new ApiCaller().callForcingExceptin("1"));
		
		return Try.of(supplier).recover(throwable -> "This is the default string, when an exception is thrown").get();
	}
	
}
