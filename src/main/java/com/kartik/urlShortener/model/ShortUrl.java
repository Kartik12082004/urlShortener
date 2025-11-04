package com.kartik.urlShortener.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "short_urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortUrl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String longUrl;
	
	@Column(nullable = false, unique = true, length = 10)
	private String code;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	private LocalDateTime expiresAt;
	
}
