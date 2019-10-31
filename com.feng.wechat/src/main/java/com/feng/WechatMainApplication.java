
package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;

@Profile("wechat")
@SpringBootApplication
public class WechatMainApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WechatMainApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WechatMainApplication.class);
	}

}
