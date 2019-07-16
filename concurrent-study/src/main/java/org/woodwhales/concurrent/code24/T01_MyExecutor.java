package org.woodwhales.concurrent.code24;

import java.util.concurrent.Executor;

/**
 *
 * Executor接口只有一个接口execute()
 *
 */
public class T01_MyExecutor implements Executor {

	public static void main(String[] args) {
		new T01_MyExecutor().execute(() -> System.out.println("hello executor"));
	}

	@Override
	public void execute(Runnable command) {
		//new Thread(command).run();
		command.run();
	}

}

