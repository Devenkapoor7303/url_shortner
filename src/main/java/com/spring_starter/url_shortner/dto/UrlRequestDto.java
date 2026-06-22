package com.spring_starter.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequestDto {
    private String longUrl;
    private String customAlias;
    private String userId;
    private LocalDateTime expiryDate;
}
