package com.feng.sportsone.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile(value = "sportsone")
@Configuration
@EnableAutoConfiguration
public class WebAppContextConfig implements WebMvcConfigurer {

	// private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");

	}

}
