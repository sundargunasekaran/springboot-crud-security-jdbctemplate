package com.springweb.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Sundar G
 * Date: 18/05/2020
 * Time: 08:12 AM
 */

@SpringBootApplication
@ComponentScan(basePackages="com.springweb.")
public class SpringBootWebApplicaiton implements CommandLineRunner {
	
	
	public static void main(String[] args) {

		SpringApplication.run(SpringBootWebApplicaiton.class, args);
		System.out.println("SpringBootWebApplicaiton Started.....");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("--> command liner started...");
		
		
	}
	
	
	
}