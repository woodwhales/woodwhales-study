package org.woodwhale.algorithm.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 霍夫曼树
 *
 */
public class HuffmanTree {

	public static void main(String[] args) {
		int[] arr = { 13, 7, 8, 3, 29, 6, 1 };
		
		Node root = createHuffmanTree(arr);
		root.preOrder();
		
	}

	private static Node createHuffmanTree(int[] arr) {
		if(arr.length == 0) {
			return null;
		}
		
		List<Node> nodeList = new ArrayList<>();
		for (int value : arr) {
			nodeList.add(new Node(value));
		}
		
		while(true) {
			/**
			 * 当结点列表里只有一个节点的时候，就直接返回当前节点（root 节点）
			 */
			if(nodeList.size() == 1) {
				return nodeList.get(0);
			}

			// 先小到大排序
			Collections.sort(nodeList);
			// 分别取出最小值和次小值
			Node left = nodeList.get(0);
			Node right = nodeList.get(1);
			// 相加得到父结点，将父结点加到列表里，把它的子结点剔除
			Node parent = new Node(left.value + right.value);
			parent.left = left;
			parent.right = right;
			nodeList.add(parent);
			
			nodeList.remove(left);
			nodeList.remove(right);
		}
	}
	
}

class Node implements Comparable<Node> {
	public int value;
	public Node left;
	public Node right;
	
	public void preOrder() {
		System.out.println(this);
		
		if(this.left != null) {
			this.left.preOrder();
		}
		
		if(this.right != null) {
			this.right.preOrder();
		}
	}
	
	public Node(int value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}

	@Override
	public int compareTo(Node node) {
		// 表示从小到大排序
		return this.value - node.value;
	}
}