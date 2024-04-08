package io.github.atomfrede.htmx_and_javaland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HtmxAndJavalandApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmxAndJavalandApplication.class, args);
	}

}
