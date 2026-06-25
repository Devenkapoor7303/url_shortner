package com.spring_starter.url_shortner.dto.CreateUrlDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUrlResponseDto {
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
