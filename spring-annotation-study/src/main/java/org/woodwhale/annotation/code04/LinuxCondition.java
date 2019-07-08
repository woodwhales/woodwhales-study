package org.woodwhale.annotation.code04;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// 获取当前容器的环境信息
		Environment environment = context.getEnvironment();
		
		// 获取系统的名称
		String osName = environment.getProperty("os.name");
		System.out.println("osName --> " + osName);
		if(osName.contains("Linux")){
			return true;
		}
		
		return false;
	}

}
