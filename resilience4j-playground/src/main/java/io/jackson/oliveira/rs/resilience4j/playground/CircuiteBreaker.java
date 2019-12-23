package io.jackson.oliveira.rs.resilience4j.playground;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
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
	
}
