package com.spring_starter.url_shortner.controller;

import com.spring_starter.url_shortner.dto.UrlRequestDto;
import com.spring_starter.url_shortner.dto.UrlResponseDto;
import com.spring_starter.url_shortner.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping()
    public ResponseEntity<UrlResponseDto> createShortUrl(@RequestBody @Valid UrlRequestDto urlRequestDto){
        UrlResponseDto urlResponseDto = urlService.createShortenUrl(urlRequestDto);
        return new ResponseEntity<>(urlResponseDto, HttpStatus.CREATED);
    }

}
