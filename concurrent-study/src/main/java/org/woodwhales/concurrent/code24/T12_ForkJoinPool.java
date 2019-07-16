package org.woodwhales.concurrent.code24;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * RecursiveAction 没有返回值，
 * RecursiveTask 可以有返回值
 */
public class T12_ForkJoinPool {
	static int[] nums = new int[1000000];
	static final int MAX_NUM = 50000;
	static Random r = new Random();
	
	static {
		for(int i=0; i<nums.length; i++) {
			nums[i] = r.nextInt(100);
		}
		
		System.out.println(Arrays.stream(nums).sum());
	}

	static class AddTask1 extends RecursiveAction {
		
		int start, end;
		
		AddTask1(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		protected void compute() {
			
			if(end-start <= MAX_NUM) {
				long sum = 0L;
				for(int i=start; i<end; i++) sum += nums[i];
				System.out.println("from:" + start + " to:" + end + " = " + sum);
			} else {
			
				int middle = start + (end-start)/2;
				
				AddTask1 subTask1 = new AddTask1(start, middle);
				AddTask1 subTask2 = new AddTask1(middle, end);
				subTask1.fork();
				subTask2.fork();
			}
		}
	}

	static class AddTask2 extends RecursiveTask<Long> {
		
		int start, end;
		
		AddTask2(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		protected Long compute() {
			
			if(end-start <= MAX_NUM) {
				long sum = 0L;
				for(int i=start; i<end; i++) sum += nums[i];
				return sum;
			} 
			
			int middle = start + (end-start)/2;
			
			AddTask2 subTask1 = new AddTask2(start, middle);
			AddTask2 subTask2 = new AddTask2(middle, end);
			subTask1.fork();
			subTask2.fork();
			
			return subTask1.join() + subTask2.join();
		}
	}
	
	public static void main(String[] args) throws IOException {

		// testRecursiveAction();

		testRecursiveTask();
	}

	public static void testRecursiveTask() throws IOException {
		ForkJoinPool fjp = new ForkJoinPool();
		AddTask2 task = new AddTask2(0, nums.length);
		fjp.execute(task);
		long result = task.join();
		System.out.println(result);
	}

	public static void testRecursiveAction() throws IOException {
		ForkJoinPool fjp = new ForkJoinPool();
		AddTask1 task = new AddTask1(0, nums.length);
		System.in.read();
	}
}
