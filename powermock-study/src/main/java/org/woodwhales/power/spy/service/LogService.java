package org.woodwhales.power.spy.service;

public class LogService {

	public void foo(String arg) {
		log(arg);
	}
	
	private void log(String arg) {
		System.out.println("i am console log : " + arg);
	}
	
	public boolean exist(String name) {
		return checkExist(name);
	}

	private boolean checkExist(String name) {
		throw new UnsupportedOperationException();
	}
}
