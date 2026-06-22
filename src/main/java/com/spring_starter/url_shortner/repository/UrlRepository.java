package com.spring_starter.url_shortner.repository;

import com.spring_starter.url_shortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
