package com.example.movietalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // @Scheduled 활성화
@EnableJpaAuditing//baseEntity 리스너 동작
@SpringBootApplication
public class MovietalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovietalkApplication.class, args);
	}

}
