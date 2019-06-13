package org.woodwhale.algorithm.huffmancode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 使用霍夫曼编码对字符串进行无损编码(压缩)
 *
 */
public class HuffmanCoding {
	public static Map<Byte, String> huffmanCodeMap = new HashMap<>();
	private static StringBuilder str = new StringBuilder("");

	public static void main(String[] args) {
		String content = "i like like like java do you like a java";

		// 编码
		byte[] huffmanCodeBytes = encode(content);
		// 解码
		String result = decode(huffmanCodeBytes, huffmanCodeMap);
		System.out.println(result);
	}

	/**
	 * 	根据霍夫曼编码表对霍夫曼编码字节数据进行解码
	 * 	思路：
	 * 		1. 根据已编码的字节数据，依次遍历，将其字节转成二进制字符
	 * 			这里要小心，字节数据转对应二进制字符
	 * 		2. 将原来的霍夫曼编码表进行k-v位置交换，然后对第1步得到的二进制字符串使用这个编码表挨个比，可以通过byte找到对应的字符的字节数据
	 * 			这里要小心，比对的时候，从二进制字符串的左边开始，只要匹配到了，就是有效数据。
	 * 		3. 将所有解码出来的字节数据转成字符串即可。
	 * @param huffmanCodeBytes
	 * @param huffmanCodeMap
	 * @return
	 */
	public static String decode(byte[] huffmanCodeBytes, Map<Byte, String> huffmanCodeMap) {

		String huffmanCodeBinaryStr = getHuffmanCodeBinaryStr(huffmanCodeBytes);
		
		byte[] bytes = decodeByHuffmanCodeMap(huffmanCodeBinaryStr, huffmanCodeMap);
		
		return new String(bytes);
	}

	/**
	 * 	将霍夫曼编码的字节数组的二进制字符串，根据霍夫曼编码表进行解码
	 * @param huffmanCodeBinaryStr	
	 * @param huffmanCodeMap
	 * @return
	 */
	private static byte[] decodeByHuffmanCodeMap(String huffmanCodeBinaryStr, Map<Byte, String> huffmanCodeMap) {
		Map<String, Byte> swapedMap = swapHuffmanCodeMap(huffmanCodeMap);

		// 因此不知道有多少字节数据，所以用列表存储已经解码的字节数据
		List<Byte> byteList = new ArrayList<>();
		
		int index = 0;
		Byte data = null;
		int count = 0;
		while(index < huffmanCodeBinaryStr.length()) {
			data = swapedMap.get(huffmanCodeBinaryStr.substring(index, index + count));
			if(data == null) {
				// 没找到就继续下一个位置找
				count++;
			} else {
				// 找到了就让index跳到下一个字节位置，同时清零计数器
				byteList.add(data);
				index += count;
				count = 0;
			}
		}
		
		byte[] result = new byte[byteList.size()];
		for (int i=0; i< byteList.size(); i++) {
			result[i] = byteList.get(i);
		}
		return result;
	}

	/**
	 * 将霍夫曼编码表的 key-value 互换，得到以二进制字节数据为key 的map
	 * @param huffmanCodeMap 原霍夫曼编码表
	 * @return
	 */
	private static Map<String, Byte> swapHuffmanCodeMap(Map<Byte, String> huffmanCodeMap) {
		Map<String, Byte> result = new HashMap<>();
		for (Map.Entry<Byte, String> entry : huffmanCodeMap.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}
		return result;
	}

	/**
	 * 	将一个byte数据转成二进制对应的十进制字符串
	 * @param data 要转的byte数据 
	 * @param needFill 标志是否需要补高位，true表示需要补，false 表示不需要补，如果是最后一个字节，不需要补高位
	 * @return data 对应的二进制字符串(注意是补码的二进制)
	 */
	private static String byteToBinaryString(byte data, boolean needFill) {
		int temp = data;
		
		// 整数需要进行补高位
		if(needFill) {
			temp |= 256; // 按位与操作， 256 => 1 0000 0000 | 1 => 0000 0001 => 1 0000 0001
		}
		
		String binaryString = Integer.toBinaryString(temp);
		if(needFill) {
			binaryString = binaryString.substring(binaryString.length() - 8);
		}
		
		return binaryString;
	}

	/**
	 * 将编码的霍夫曼编码字节数组转成二进制数据的十进制字符串
	 * 
	 * @param huffmanCodeBytes
	 * @return
	 */
	private static String getHuffmanCodeBinaryStr(byte[] huffmanCodeBytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < huffmanCodeBytes.length; i++) {
			// 最后一位字节数据不能补高位，补了就是前面多了0，那么解码就会失败
			boolean needFill = (i == huffmanCodeBytes.length - 1);
			
			String binaryString = byteToBinaryString(huffmanCodeBytes[i], !needFill);
			sb.append(binaryString);
		}
		
		System.out.println("解码 -> " + sb.toString());
		
		return sb.toString();
	}

	/**
	 * 将字符串根据霍夫曼编码进行无损压缩
	 * 
	 * 思路： 1. 将字符串拆成字符数组 2. 将所有字符数组进行重复值统计，生成对应的带权重的结点列表，权重就是重复数量 3.
	 * 将带权重的结点列表依次遍历，转成霍夫曼树 4. 遍历霍夫曼树，左边路径是0，右边路径是1，得到霍夫曼编码表 5.
	 * 将原数组的字符串根据第4步得到的霍夫曼编码表进行编码，得到霍夫曼压缩数据的二进制信息的十进制字符串 6.
	 * 将第5步的十进制字符串（本质是表示数据的位数信息）转成字节数组，每8个数字表示一个byte
	 * 
	 * @param content 要压缩的原字符串
	 * @return 压缩后的字符串数据
	 */
	public static byte[] encode(String content) {
		byte[] contentBytes = content.getBytes();
		return encode(contentBytes);
	}
	
	/**
	 * 	将字符串根据霍夫曼编码进行无损压缩
	 * @param contentBytes
	 * @return
	 */
	public static byte[] encode(byte[] contentBytes) {
		// 字符数组转Node列表
		List<Node> nodeList = createNodeList(contentBytes);

		// Node 列表转成霍夫曼树
		Node root = createHuffmanTree(nodeList);

		// 通过霍夫曼树得到压缩后的霍夫曼编码
		Map<Byte, String> huffmanCodeMap = getCodes(root);

		// 根据霍夫曼编码表将字符串数组进行压缩
		byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodeMap);

		return huffmanCodeBytes;
	}
	

	/**
	 * 将原字符数组根据霍夫曼编码表进行压缩
	 * 
	 * @param contentBytes   原字符串字符数组
	 * @param huffmanCodeMap 霍夫曼编码表
	 * @return
	 */
	private static byte[] zip(byte[] contentBytes, Map<Byte, String> huffmanCodeMap) {
		if (contentBytes.length == 0 || huffmanCodeMap == null) {
			return null;
		}

		StringBuilder huffmanCodeBinaryStr = new StringBuilder();

		// 原字符数组依次遍历，根据霍夫曼编码表进行转码
		for (byte data : contentBytes) {
			huffmanCodeBinaryStr.append(huffmanCodeMap.get(data));
		}

		System.out.println("编码 -> " + huffmanCodeBinaryStr.toString());
		
		/**
		 * byteStr 是字符串，其中的每一个值就是对应要返回数组的位数值 因此，这个字符串的长度必须是8的位数
		 * 
		 * int len = 0; if(huffmanStr.length() % 8 == 0) { len = huffmanStr.length() /
		 * 8; } else { len = huffmanStr.length() / 8 + 1; }
		 * 
		 * 等价于 (huffmanStr.length()+7) / 8
		 */
		int len = (huffmanCodeBinaryStr.length() + 7) >>> 3;

		byte[] huffmanCodeBytes = new byte[len];

		for (int i = 0, index = 0; i < huffmanCodeBinaryStr.length(); i += 8) {
			String byteStr;
			// 截取到最后，不足8位，就全部截取
			if (i + 8 > huffmanCodeBinaryStr.length()) {
				byteStr = huffmanCodeBinaryStr.substring(i);
			} else {
				byteStr = huffmanCodeBinaryStr.substring(i, i + 8);
			}
			// 将当前字符串转先转成二进制，再强转成byte
			huffmanCodeBytes[index++] = (byte) Integer.parseInt(byteStr, 2);
		}

		return huffmanCodeBytes;
	}

	/**
	 * 根据霍夫曼树生成霍夫曼编码表
	 * 
	 * @param root 霍夫曼树根节点
	 * @return
	 */
	private static Map<Byte, String> getCodes(Node root) {
		if (root == null) {
			return null;
		}

		// 从左压缩
		getCodes(root.left, "0", str);
		// 从右压缩
		getCodes(root.right, "1", str);
		return huffmanCodeMap;
	}

	/**
	 * 根据当前结点，生成霍夫曼编码表
	 * 	前缀编码：向左的路径为0，向右的路径为1
	 * 
	 * @param node
	 * @param code
	 * @param str
	 * @return
	 */
	private static void getCodes(Node node, String code, StringBuilder str) {
		StringBuilder stringBuffer = new StringBuilder(str);

		stringBuffer.append(code);

		if (node != null) {
			if (node.data == null) {
				getCodes(node.left, "0", stringBuffer);
				getCodes(node.right, "1", stringBuffer);
			} else {
				huffmanCodeMap.put(node.data, stringBuffer.toString());
			}
		}
	}

	/**
	 * 前序遍历霍夫曼树
	 * 
	 * @param root
	 */
	public static void preOrder(Node root) {
		if (root == null) {
			System.out.println("霍夫曼树根节点为空！");
			return;
		}

		root.preOrder();
	}

	/**
	 * 将结点列表转成霍夫曼树
	 * 
	 * @param nodeList 霍夫曼结点列表
	 * @return
	 */
	private static Node createHuffmanTree(List<Node> nodeList) {
		if (nodeList.isEmpty()) {
			return null;
		}

		while (true) {
			if (nodeList.size() == 1) {
				return nodeList.get(0);
			}

			Collections.sort(nodeList);
			Node left = nodeList.get(0);
			Node right = nodeList.get(1);
			Node parent = new Node(null, left.weight + right.weight);
			parent.left = left;
			parent.right = right;
			nodeList.remove(left);
			nodeList.remove(right);
			nodeList.add(parent);
		}
	}

	/**
	 * 将字符数组转成结点列表 相同字符的权重会增加，有多少重复字符就有多少权重
	 * 
	 * @param contentBytes 字符数组
	 * @return
	 */
	private static List<Node> createNodeList(byte[] contentBytes) {
		Map<Byte, Integer> counters = new HashMap<>();
		for (byte data : contentBytes) {
			Integer weight = counters.get(data);
			if (weight == null) {
				counters.put(data, 1);
			} else {
				counters.put(data, weight + 1);
			}
		}

		return counters.entrySet().stream().map(counter -> new Node(counter.getKey(), counter.getValue()))
				.collect(Collectors.toList());
	}
}

class Node implements Comparable<Node> {

	public Byte data; // 字符数据
	public int weight; // 字符出现的次数
	public Node left;
	public Node right;

	public Node(Byte data, int weight) {
		this.weight = weight;
		this.data = data;
	}

	public void preOrder() {
		System.out.println(this);

		if (this.left != null) {
			this.left.preOrder();
		}

		if (this.right != null) {
			this.right.preOrder();
		}
	}

	@Override
	public String toString() {
		return "Node [data=" + data + ", weight=" + weight + "]";
	}

	@Override
	public int compareTo(Node node) {
		// 从小到大排序
		return this.weight - node.weight;
	}
}