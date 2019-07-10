package org.woodwhales.spring.code1;

import lombok.Setter;

@Setter
public class HelloWorld {
	
	public HelloWorld() {
		System.out.println("HelloWorld...Constructor...");
	}

	private String name;
	
	public void hello() {
		System.out.println("hello -> " + name);
	}
}
