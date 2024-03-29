package org.woodwhales.zk.demo2.utils;
import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class AclUtils {
	
	public static String getDigestUserPwd(String id) throws Exception {
		return DigestAuthenticationProvider.generateDigest(id);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException, Exception {
		String id = "woodwhales:woodwhales";
		String idDigested = getDigestUserPwd(id);
		System.out.println(idDigested);
	}
}