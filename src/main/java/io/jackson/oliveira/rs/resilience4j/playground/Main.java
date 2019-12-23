package io.jackson.oliveira.rs.resilience4j.playground;

public class Main {
	
	public static void main(String[] args) {
		System.out.println(new CircuiteBreaker().buildCircuiteWithCustomConfigs());
	}

}
