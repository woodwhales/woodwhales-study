package org.woodwhale.datastructure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 	图
 * 	深度遍历
 * 	英文缩写为 DFS 即 Depth First Search
 * 
 *
 */
public class Graph {
	
	public static void main(String[] args) {
		int numOfPoint = 5;
		
		String vertexs[] = {"A", "B", "C", "D", "E"};
		
		Graph graph = new Graph(numOfPoint);
		
		for (String vertex : vertexs) {
			graph.insetVertex(vertex);
		}
		
		graph.insertEdge(0, 1, 1); // A-B
		graph.insertEdge(0, 2, 1); // A-C
		graph.insertEdge(1, 2, 1); // B-C
		graph.insertEdge(1, 3, 1); // B-D
		graph.insertEdge(1, 4, 1); // B-E
		
		System.out.println(Arrays.toString(vertexs));
		graph.showGraph();
		
		System.out.println("深度遍历");
		graph.dfs();
		
		System.out.println();
		System.out.println("广度遍历");
		graph.bfs();
	}
	
	private ArrayList<String> vertextList; //存储顶点的集合
	private int[][] edges; // 存储图对应的邻接矩阵
	private int numOfEdges; // 表示边的数量
	private boolean[] isVisited; // 定义数组用于记录结点是否被访问过
	
	private void bfs(int index) {
		System.out.print(getVertexByIndex(index) +" -> ");
		isVisited[index] = true;
		
		LinkedList<Integer> queue = new LinkedList<>();
		queue.addLast(index);
		
		while(!queue.isEmpty()) {
			int currentIndex = queue.removeFirst();
			int firstNeighbor = getFirstNeighbor(currentIndex);
			while(firstNeighbor != -1) {
				if(!isVisited[firstNeighbor]) {
					System.out.print(getVertexByIndex(firstNeighbor) +" -> ");
					isVisited[firstNeighbor] = true;
					queue.addLast(firstNeighbor);
				}
				
				firstNeighbor = getNextNeighbor(index, firstNeighbor);
			}
		}
		
	}
	
	/**
	 * 	广度优先遍历
	 */
	public void bfs() {
		this.isVisited = new boolean[this.vertextList.size()];
		for(int i = 0; i < getNumOfVertex(); i++) {
			if(!isVisited[i]) {
				bfs(i);
			}
		}
	}
	
	private void dfs(int index) {
		System.out.print(getVertexByIndex(index) + " -> ");
		this.isVisited[index] = true;
		
		int firstNeighborOfIndex = getFirstNeighbor(index);
		while(firstNeighborOfIndex != -1) {
			if(!isVisited[firstNeighborOfIndex]) {
				dfs(firstNeighborOfIndex);
			}
			
			firstNeighborOfIndex = getNextNeighbor(index, firstNeighborOfIndex);
		}
	}
	
	/**
	 * 	深度遍历（递归实现）
	 */
	public void dfs() {
		this.isVisited = new boolean[this.vertextList.size()];
		for(int i = 0; i < getNumOfVertex(); i++) {
			if(!isVisited[i]) {
				dfs(i);
			}
		}
	}
	
	/**
	 * 	根据前一个邻接结点的下标来获取下一个邻接结点
	 * @param vertexOfIndex1
	 * @param vertexOfIndex2
	 * @return
	 */
	public int getNextNeighbor(int vertexOfIndex1, int vertexOfIndex2) {
		for(int i = vertexOfIndex2 + 1; i < vertextList.size() ; i++) {
			if(edges[vertexOfIndex1][i] > 0) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 	index 对应的结点，相连接的第一个结点的下标
	 * 
	 * @param index
	 * @return 如果存在则返回对应的下标，否则返回-1
	 */
	public int getFirstNeighbor(int index) {
		for(int i = 0; i < vertextList.size(); i++) {
			if(edges[index][i] > 0) {
				return i;
			}
		}
		return -1;
	}
	
	/** 
	 * 	根据顶点个数进行图的初始化
	 * @param numOfPoint
	 */
	public Graph(int numOfPoint) {
		this.vertextList = new ArrayList<>(numOfPoint);
		this.edges = new int[numOfPoint][numOfPoint];
		this.numOfEdges = 0;
	}
	
	/**
	 * 	显示图对应的矩阵
	 */
	public void showGraph() {
		for (int[] row : edges) {
			System.out.println(Arrays.toString(row));
		}
	}
	
	public int getNumOfVertex() {
		return this.vertextList.size();
	}
	
	/**
	 * 	得到边的数量
	 * @return
	 */
	public int getNumOfEdges() {
		return this.numOfEdges;
	}
	
	/**
	 * 	得到两个顶点之间的权重
	 * @param vertexOfIndex1
	 * @param vertexOfIndex2
	 * @return
	 */
	public int getWeight(int vertexOfIndex1, int vertexOfIndex2) {
		return this.edges[vertexOfIndex1][vertexOfIndex2];
	}
	
	/**
	 * 	根据索引获取顶点
	 * @param index
	 * @return
	 */
	public String getVertexByIndex(int index) {
		return this.vertextList.get(index);
	}
	
	/**
	 * 	插入结点
	 * @param vertex
	 */
	public void insetVertex(String vertex) {
		this.vertextList.add(vertex);
	}
	
	/**
	 * 	插入一条边
	 * @param vertexOfIndex1 表示点的下标，即是第几个顶点 "A"-"B" "A"->0 "B"->1
	 * @param vertexOfIndex2 第二个顶点对应的下标
	 * @param weight 表示边的权限
	 */
	public void insertEdge(int vertexOfIndex1, int vertexOfIndex2, int weight) {
		this.edges[vertexOfIndex1][vertexOfIndex2] = weight;
		this.edges[vertexOfIndex2][vertexOfIndex1] = weight;
		this.numOfEdges++;
	}
	
}
