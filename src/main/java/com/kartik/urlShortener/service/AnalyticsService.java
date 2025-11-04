package com.kartik.urlShortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

	public static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class); 
	
	@Async
	public void logClick(String code) {
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		logger.info("CLICK LOGGED (Async): Code '{}' was accessed", code);
	}

}
