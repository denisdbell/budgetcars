package com.bugdetcars.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bugdetcars.net.scan.Scan;

@SpringBootApplication
@EnableJpaRepositories("com.budgetcars.net.repository")
public class Application  implements CommandLineRunner  {
	
	@Autowired
	Scan autoAdScan;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		autoAdScan.scanAll(autoAdScan.maxPageCount);
	}  
}
