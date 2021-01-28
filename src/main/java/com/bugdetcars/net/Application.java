package com.bugdetcars.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bugdetcars.net.scan.AutoAdsScan;
import com.bugdetcars.net.scan.JaCarsScan;

@SpringBootApplication
@EnableJpaRepositories("com.budgetcars.net.repository")
public class Application implements CommandLineRunner {

	@Autowired
	AutoAdsScan autoAdScan;
	@Autowired
	JaCarsScan jaCarsScan;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length != 0) {
			if (args[0].equals("SCAN_ALL")) {
				autoAdScan.scanAll(autoAdScan.getMaxPageCount());
				jaCarsScan.setMaxPageCountFromPagination();
				jaCarsScan.scanAll(jaCarsScan.getMaxPageCount());
			}
		}
	}
}
