package com.kartik.urlShortener.dto.request;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenRequest {
	
	@NotBlank(message = "URL must not be blank")
	@URL(message = "Must be a valid URL")
	private String longUrl;
	
	private String customCode;
	
	private LocalDateTime expiresAt;
	
}
