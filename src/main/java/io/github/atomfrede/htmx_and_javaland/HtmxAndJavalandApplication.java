package io.github.atomfrede.htmx_and_javaland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class HtmxAndJavalandApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmxAndJavalandApplication.class, args);
	}

}
