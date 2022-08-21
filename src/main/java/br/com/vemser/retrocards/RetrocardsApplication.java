package br.com.vemser.retrocards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RetrocardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetrocardsApplication.class, args);
	}

}
