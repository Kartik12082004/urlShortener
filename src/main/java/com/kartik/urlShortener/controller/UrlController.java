package com.kartik.urlShortener.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kartik.urlShortener.dto.request.ShortenRequest;
import com.kartik.urlShortener.dto.response.ShortUrlResponse;
import com.kartik.urlShortener.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class UrlController {
	
	private final UrlService urlService;
	
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}
	
	@PostMapping("/api/shorten")
	public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody @Valid ShortenRequest request){
		ShortUrlResponse response = urlService.createShortUrl(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/{code}")
	public void redirect(@PathVariable String code, HttpServletResponse httpServletResponse) throws IOException{
		String longUrl = urlService.getLongUrl(code);
		httpServletResponse.sendRedirect(longUrl);
	}
	
}
