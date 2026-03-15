package com.kartik.urlShortener.dto.request;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShortenRequest {
	
	@NotBlank(message = "URL must not be blank")
	@URL(message = "Must be a valid URL")
	private String longUrl;
	
	@Pattern(regexp = "^[a-zA-Z0-9-_]*$", message = "Custom code can only contain letters, numbers, hyphens, or underscores")
	@Size(min = 1, max = 10, message = "Custom code must be between 1 and 10 characters")
	private String customCode;
	
	private LocalDateTime expiresAt;
	
}
