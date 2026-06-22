package com.spring_starter.url_shortner.dto;

import com.spring_starter.url_shortner.entity.Analytics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrlResponseDto {
    private Long id;
    private String shortenUrl;
    private boolean isActive;
    private String longUrl;
    private String customAlias;
    private String userId;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
