package org.woodwhale.datastructure.huffmancode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class HuffmanCodingForZipFile {

	public static void main(String[] args) {
		zipFile(new File("c://src.png"), new File("C://app//src.zip"));
		System.out.println("zip success");
	}

	
	public static void zipFile(File src, File dest) {
		if(src == null || dest == null) {
			return;
		}
		
		FileInputStream is = null;
		OutputStream os = null;
		ObjectOutputStream oos = null;
		try {
			is = new FileInputStream(src);
			byte[] data = new byte[is.available()]; 
			
			is.read(data);
			byte[] huffmanCodes = HuffmanCoding.encode(data);
			
			os = new FileOutputStream(dest);
			oos = new ObjectOutputStream(os);
			oos.writeObject(huffmanCodes);
			
			oos.writeObject(HuffmanCoding.huffmanCodeMap);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
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
