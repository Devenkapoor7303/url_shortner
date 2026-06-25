package com.spring_starter.url_shortner.service;

import com.spring_starter.url_shortner.dto.CreateUrlDto.CreateUrlRequestDto;
import com.spring_starter.url_shortner.dto.CreateUrlDto.CreateUrlResponseDto;
import com.spring_starter.url_shortner.entity.Url;
import com.spring_starter.url_shortner.repository.UrlRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlService {

    @Value("${app.base-url}")
    private String baseUrl;

    private final UrlRepository urlRepository;
    private final ModelMapper modelMapper;

    public UrlService(UrlRepository urlRepository, ModelMapper modelMapper){
        this.urlRepository = urlRepository;
        this.modelMapper = modelMapper;
    }

    public CreateUrlResponseDto createShortenUrl(CreateUrlRequestDto createUrlRequestDto, String userId){
        String shortCode = (createUrlRequestDto.getCustomAlias() != null && !createUrlRequestDto.getCustomAlias().isBlank())
                ? createUrlRequestDto.getCustomAlias()
                : UUID.randomUUID().toString().substring(0, 8);

        Url url = Url.builder()
                .longUrl(createUrlRequestDto.getLongUrl())
                .customAlias(createUrlRequestDto.getCustomAlias())
                .userId(userId)
                .expiryDate(createUrlRequestDto.getExpiryDate())
                .shortenCode(shortCode)
                .isActive(true)
                .build();

        Url savedUrl = urlRepository.save(url);

        return mapToResponseDto(savedUrl);
    }

    private CreateUrlResponseDto mapToResponseDto(Url url) {
        CreateUrlResponseDto dto = modelMapper.map(url, CreateUrlResponseDto.class);
        dto.setActive(url.isActive());
        dto.setShortenUrl(baseUrl + "/s/" + url.getShortenCode());
        return dto;
    }

}
