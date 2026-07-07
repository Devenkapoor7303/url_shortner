package com.spring_starter.url_shortner.service;

import com.spring_starter.url_shortner.dto.createUrlDto.CreateUrlRequestDto;
import com.spring_starter.url_shortner.dto.createUrlDto.CreateUrlResponseDto;
import com.spring_starter.url_shortner.dto.getUrlDto.GetUrlResponseDto;
import com.spring_starter.url_shortner.dto.updateUrlDto.UpdateUrlRequestDto;
import com.spring_starter.url_shortner.dto.updateUrlDto.UpdateUrlResponseDto;
import com.spring_starter.url_shortner.entity.Url;
import com.spring_starter.url_shortner.exception.ResourceNotFoundException;
import com.spring_starter.url_shortner.repository.UrlRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public String getLongUrl(String shortenCode){
        Url url = urlRepository.findByShortenCode(shortenCode)
                .orElseThrow(()->new ResourceNotFoundException("Long url does not exist for particular code"));

        boolean isExpired = url.isExpired();
        if( isExpired ){
            throw new ResourceNotFoundException("Url is Expired");
        }

        return url.getLongUrl();
    }

    public Page<GetUrlResponseDto> getShortUrls(Pageable pageable) {
        return urlRepository.findAll(pageable)
                .map(urlEntity -> modelMapper.map(urlEntity, GetUrlResponseDto.class));
    }

    @Transactional
    public Boolean deleteShortenCode(String shortenCode){
        isExistByShortenCode(shortenCode);
        urlRepository.deleteByShortenCode(shortenCode);
        return true;
    }

    public UpdateUrlResponseDto updateShortenedUrl(String shortenCode, UpdateUrlRequestDto updateUrlRequestDto){
        Url url = isExistByShortenCode(shortenCode);
        modelMapper.map(updateUrlRequestDto, url);
        Url updatedUrl = urlRepository.save(url);
        return modelMapper.map(updatedUrl, UpdateUrlResponseDto.class);
    }

    private CreateUrlResponseDto mapToResponseDto(Url url) {
        CreateUrlResponseDto dto = modelMapper.map(url, CreateUrlResponseDto.class);
        dto.setActive(url.isActive());
        dto.setShortenUrl(baseUrl + "/s/" + url.getShortenCode());
        return dto;
    }

    private Url isExistByShortenCode(String shortenCode){
        return urlRepository.findByShortenCode(shortenCode).orElseThrow(()->new ResourceNotFoundException("Url is not found with given code"));
    }

}
