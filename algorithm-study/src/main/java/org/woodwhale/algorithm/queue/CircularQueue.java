package org.woodwhale.algorithm.queue;

/**
 * 《数据结构与算法之美》 源码
 * Created by wangzheng on 2018/10/9.
 * Updated by woodwhales on 2019/06/04.
 */
public class CircularQueue {
  // 数组：items，数组大小：n
  private String[] items;
  private int arrSize = 0;
  // head表示队头下标，tail表示队尾下标
  private int head = 0;
  private int tail = 0;

  // 申请一个大小为capacity的数组
  public CircularQueue(int capacity) {
    items = new String[capacity];
    arrSize = capacity;
  }

  // 入队
  public boolean enqueue(String item) {
    // 队列满了
    if ((tail + 1) % arrSize == head) {
    	return false;
    }
    
    items[tail] = item;
    tail = (tail + 1) % arrSize;
    return true;
  }

  // 出队
  public String dequeue() {
    // 如果head == tail 表示队列为空
    if (head == tail) {
    	return null;
    }
    
    String ret = items[head];
    head = (head + 1) % arrSize;
    return ret;
  }

  public void printAll() {
    if (0 == arrSize) return;
    for (int i = head; i % arrSize != tail; ++i) {
      System.out.print(items[i] + " ");
    }
    System.out.println();
  }
}