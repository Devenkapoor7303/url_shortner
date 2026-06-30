package com.spring_starter.url_shortner.dto.getUrlDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUrlResponseDto {
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
