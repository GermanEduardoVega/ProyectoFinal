package io.anchormind.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnchorMindProApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnchorMindProApplication.class, args);
		System.out.println("Estoy funcinando");
	}
}
