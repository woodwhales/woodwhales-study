package org.woodwhale.algorithm.huffmancode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * 	霍夫曼编码应用
 *	应用场景：
 *		源文件重复数据比较少，不适用于霍夫曼编码压缩
 *		因此，已经压缩的文件，不适用于霍夫曼编码压缩
 *		霍夫曼编码可对任意的二进制数据进行编码压缩
 */
public class HuffmanCodingForZipFile {

	public static void main(String[] args) {
		zipFile(new File("c://src.jpg"), new File("C://src.zip"));
		System.out.println("zip success");
		
		zipFile(new File("C://src.zip"), new File("C://src2.jpg"));
		System.out.println("unzip success");
	}
	
	public static void unZipFile(File zipFile, File dest) {
		if(zipFile == null || dest == null) {
			return;
		}
		
		FileInputStream is = null;
		ObjectInputStream ois = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(zipFile);
			
			ois = new ObjectInputStream(is);
			
			byte[] huffmanBytes = (byte[])ois.readObject();
			Map<Byte, String> huffmanCodeMap = (Map<Byte, String>)ois.readObject();
			
			byte[] bytes = HuffmanCoding.decode(huffmanBytes, huffmanCodeMap);
			
			os = new FileOutputStream(dest);
			os.write(bytes);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(os != null) {
					os.close();
				}
				
				if(ois != null) {
					ois.close();
				}
				
				if(is != null) {
					is.close();
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public static void zipFile(File srcFile, File dest) {
		if(srcFile == null || dest == null) {
			return;
		}
		
		FileInputStream is = null;
		OutputStream os = null;
		ObjectOutputStream oos = null;
		try {
			is = new FileInputStream(srcFile);
			// 读取源文件的所有字节数据
			System.out.println(is.available());
			byte[] data = new byte[is.available()]; 
			
			is.read(data);
			byte[] huffmanCodes = HuffmanCoding.encode(data);
			
			os = new FileOutputStream(dest);
			oos = new ObjectOutputStream(os);
			oos.writeObject(huffmanCodes);
			
			oos.writeObject(HuffmanCoding.huffmanCodeMap);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(oos != null) {
					oos.close();
				}
				
				if(os != null) {
					os.close();
				}
				
				if(is != null) {
					is.close();
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		
	}
	
}
