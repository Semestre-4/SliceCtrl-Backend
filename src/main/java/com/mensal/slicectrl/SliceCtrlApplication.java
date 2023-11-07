package com.mensal.slicectrl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


/**
 * @author Bouchra Akl
 * @author Gustavo Piegat
 */

@SpringBootApplication
public class SliceCtrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SliceCtrlApplication.class, args);
	}

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("Content-Type")
				.allowCredentials(true);
	}

}
