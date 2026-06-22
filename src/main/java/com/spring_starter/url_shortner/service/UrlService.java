package com.spring_starter.url_shortner.service;

import com.spring_starter.url_shortner.dto.UrlRequestDto;
import com.spring_starter.url_shortner.dto.UrlResponseDto;
import com.spring_starter.url_shortner.entity.Url;
import com.spring_starter.url_shortner.repository.UrlRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlService {

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private final UrlRepository urlRepository;
    private final ModelMapper modelMapper;

    public UrlService(UrlRepository urlRepository, ModelMapper modelMapper){
        this.urlRepository = urlRepository;
        this.modelMapper = modelMapper;
    }

    public UrlResponseDto createShortenUrl(UrlRequestDto urlRequestDto){
        String shortCode = (urlRequestDto.getCustomAlias() != null && !urlRequestDto.getCustomAlias().isBlank())
                ? urlRequestDto.getCustomAlias()
                : UUID.randomUUID().toString().substring(0, 8);

        Url url = Url.builder()
                .longUrl(urlRequestDto.getLongUrl())
                .customAlias(urlRequestDto.getCustomAlias())
                .userId(urlRequestDto.getUserId())
                .expiryDate(urlRequestDto.getExpiryDate())
                .shortenCode(shortCode)
                .isActive(true)
                .build();

        Url savedUrl = urlRepository.save(url);

        return mapToResponseDto(savedUrl);
    }

    private UrlResponseDto mapToResponseDto(Url url) {
        UrlResponseDto dto = modelMapper.map(url, UrlResponseDto.class);
        dto.setActive(url.isActive());
        dto.setShortenUrl(baseUrl + "/s/" + url.getShortenCode());
        return dto;
    }

}
