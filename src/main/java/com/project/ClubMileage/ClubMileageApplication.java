package com.project.ClubMileage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //시간 자동 변경
@SpringBootApplication
public class ClubMileageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubMileageApplication.class, args);
	}

}
