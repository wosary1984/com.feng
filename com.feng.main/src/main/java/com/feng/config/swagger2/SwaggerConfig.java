package com.feng.config.swagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${swagger2.title}")
	public String title;

	@Value("${swagger2.desc}")
	public String desc;

	@Value("${swagger2.contact.name}")
	public String name;

	@Value("${swagger2.contact.url}")
	public String url;

	@Value("${swagger2.contact.email}")
	public String email;

	@Value("${swagger2.version}")
	public String version;

	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title(title).description(desc).contact(new Contact(name, url, email))
						.version(version).build())
				.select().apis(RequestHandlerSelectors.basePackage("com.feng")).paths(PathSelectors.any()).build();
	}
}
