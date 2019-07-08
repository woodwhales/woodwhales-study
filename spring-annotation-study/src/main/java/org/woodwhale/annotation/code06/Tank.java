package org.woodwhale.annotation.code06;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Tank implements InitializingBean, DisposableBean {
	
	public Tank() {
		System.out.println("tank construct...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("tank --> DisposableBean --> destroy");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("tank --> InitializingBean --> afterPropertiesSet");
	}

}
