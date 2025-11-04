package com.kartik.urlShortener.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kartik.urlShortener.model.ShortUrl;


@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl	, Long> {
	
	Optional<ShortUrl> findByCode(String code);
	
	boolean existsByCode(String code);
	
	List<ShortUrl> findAllByExpiresAtBefore(LocalDateTime dateTime);
	
}
