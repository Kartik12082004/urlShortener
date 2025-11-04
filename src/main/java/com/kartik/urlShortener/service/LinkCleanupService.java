package com.kartik.urlShortener.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kartik.urlShortener.model.ShortUrl;
import com.kartik.urlShortener.repository.ShortUrlRepository;

@Service
public class LinkCleanupService {
	
	private static final Logger logger = LoggerFactory.getLogger(LinkCleanupService.class);
	private final ShortUrlRepository repository;
	private final CacheManager cacheManager;
	
	public LinkCleanupService(ShortUrlRepository repository, CacheManager cacheManager) {
		this.repository = repository;
		this.cacheManager = cacheManager;	
	}
	
	@Scheduled(cron = "0 0 * * * *")
	public void cleanupExpiredLinks() {
		logger.info("Running expired link cleanup job....");
		
		List<ShortUrl> expiredLinks = repository.findAllByExpiresAtBefore(LocalDateTime.now());
		
		if(expiredLinks.isEmpty()) {
			logger.info("NO expired links found");
			return;
		}
		
		logger.info("Found {} expired links to delete", expiredLinks.size());
		
		repository.deleteAll(expiredLinks);
		
		for(ShortUrl link : expiredLinks) {
			Objects.requireNonNull(cacheManager.getCache("urls")).evict(link.getCode());
		}
		logger.info("Cleanup job finished");
	}	
}
