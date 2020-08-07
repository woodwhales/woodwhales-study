package org.woodwhales.swagger.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author woodwhales on 2020-08-07
 * @description
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan
public class SwaggerConfig {

	@Bean
	public Docket preview() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.groupName("后端")
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.woodwhales.swagger.controller.bg"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public Docket back() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.groupName("前端")
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.woodwhales.swagger.controller"))
				.apis(input -> {
					Class<?> declaringClass = input.declaringClass();
					boolean annotationPresent = declaringClass.isAnnotationPresent(RestController.class);
					if(!StringUtils.startsWith(declaringClass.getName(), "org.woodwhales.swagger.controller.bg")
							&& annotationPresent) {
						return true;
					}
					return false;
				})
				.build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("woodwhales", "http://woodwhales.cn/", "woodwhales@163.com");
		return new ApiInfoBuilder().title("swagger入门教程")
							.contact(contact)
							.description("这里是 RESTful API 描述")
							.version("1.0.1").build();
	}
}