package com.java.clip.challenge.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClipCodeChallengeApplication {

	public static final Logger log = LogManager.getLogger(ClipCodeChallengeApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(ClipCodeChallengeApplication.class, args);
		
		log.info("               _ _                       _             _           _ _                      ");                       
		log.info("	   ___| (_)_ __     ___ ___   __| | ___    ___| |__   __ _| | | ___ _ __   __ _  ___ ");
		log.info("	  / __| | | '_ \\   / __/ _ \\ / _` |/ _ \\  / __| '_ \\ / _` | | |/ _ \\ '_ \\ / _` |/ _ \\");
		log.info("	 | (__| | | |_) | | (_| (_) | (_| |  __/ | (__| | | | (_| | | |  __/ | | | (_| |  __/");
		log.info("	  \\___|_|_| .__/   \\___\\___/ \\__,_|\\___|  \\___|_| |_|\\__,_|_|_|\\___|_| |_|\\__, |\\___|");
		log.info("	          |_|                                                             |___/      ");
	}

}
