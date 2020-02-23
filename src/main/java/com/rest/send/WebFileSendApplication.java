package com.rest.send;

import com.rest.send.service.TomcatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebFileSendApplication {
	@Autowired
	TomcatConfig tomcatConfig;

	public static void main(String[] args) {
		SpringApplication.run(WebFileSendApplication.class, args);
	}
}
