package com.example.demoexpiremap;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demoexpiremap.domain.BizBox;

@SpringBootApplication
public class DemoExpireMapApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoExpireMapApplication.class, args);
	}

	@Bean
	public ApplicationRunner run(BizBox bizBox) {
		return args -> {
			bizBox.put("k1", "하나", 1000);
			bizBox.put("k2", "둘", 3000);
			bizBox.put("k3", "셋", 2000);
			bizBox.startCleanup();

			System.out.println("---0s after");
			bizBox.print();

			TimeUnit.SECONDS.sleep(1);
			System.out.println("---1s after");
			bizBox.print();

			TimeUnit.SECONDS.sleep(1);
			System.out.println("---2s after");
			bizBox.print();
			TimeUnit.SECONDS.sleep(1);

			System.out.println("---3s after");
			bizBox.print();
		};
	}

}
