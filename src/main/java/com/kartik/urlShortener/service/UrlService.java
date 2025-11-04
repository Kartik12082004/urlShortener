package com.kartik.urlShortener.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.kartik.urlShortener.UrlShortenerApplication;
import com.kartik.urlShortener.dto.request.ShortenRequest;
import com.kartik.urlShortener.dto.response.ShortUrlResponse;
import com.kartik.urlShortener.model.ShortUrl;
import com.kartik.urlShortener.repository.ShortUrlRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UrlService {
	
	private final ShortUrlRepository repository;
	private final AnalyticsService analyticsService;
	private final String baseUrl;
	
	public UrlService(ShortUrlRepository repository,
			AnalyticsService analyticsService,
			@Value("${app.base-url}") String baseUrl) {
		this.repository = repository;
		this.analyticsService = analyticsService;
		this.baseUrl = baseUrl;
	}
	
	public ShortUrlResponse createShortUrl(ShortenRequest request) {
		String code;
		
		if (request.getCustomCode() != null && !request.getCustomCode().isEmpty()) {
			code = request.getCustomCode();
			
			if (repository.existsByCode(code)) {
				throw new IllegalArgumentException("Custom code is already in use");
			}
		}else {
			code = generateUniqueCode();
		}
		
		ShortUrl shortUrl = ShortUrl.builder()
				.longUrl(request.getLongUrl())
				.code(code)
				.createdAt(LocalDateTime.now())
				.expiresAt(request.getExpiresAt())
				.build();
		
		repository.save(shortUrl);
		
		return ShortUrlResponse.builder()
				.shortUrl(baseUrl + code)
				.longUrl(shortUrl.getLongUrl())
				.expiresAt(shortUrl.getExpiresAt())
				.build();
	}
	
	private String generateUniqueCode() {
		String code;
		
		do {
			code = RandomStringUtils.randomAlphanumeric(6);
		} while (repository.existsByCode(code));
		
		return code;
	}
	
	@Cacheable(value = "urls", key = "#code")
	public String getLongUrl(String code) {
		ShortUrl shortUrl = repository.findByCode(code)
				.orElseThrow(() -> new EntityNotFoundException("No Url found for the code: " + code));
		
		if (shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(LocalDateTime.now())) {
			repository.delete(shortUrl);
			throw new EntityNotFoundException("Link expired and was deleted");
		}
		
		analyticsService.logClick(code);
		
		return shortUrl.getLongUrl();
	}
	
	@CacheEvict(value = "urls", key = "#code")
	public void deleteShortUrl(String code) {
		ShortUrl shortUrl = repository.findByCode(code)
				.orElseThrow(() -> new EntityNotFoundException("No URL found for the code: " + code));
		repository.delete(shortUrl);
	}
	
}