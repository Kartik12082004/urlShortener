package com.kartik.urlShortener.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortUrlResponse {
	
	private String shortUrl;
	private String longUrl;
	private LocalDateTime expiresAt;
	
}
