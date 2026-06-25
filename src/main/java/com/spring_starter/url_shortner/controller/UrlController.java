package com.spring_starter.url_shortner.controller;

import com.spring_starter.url_shortner.dto.CreateUrlDto.CreateUrlRequestDto;
import com.spring_starter.url_shortner.dto.CreateUrlDto.CreateUrlResponseDto;
import com.spring_starter.url_shortner.service.UrlService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping()
    public ResponseEntity<CreateUrlResponseDto> createShortUrl(
            @RequestBody @Valid CreateUrlRequestDto createUrlRequestDto,
            @RequestHeader(value = "x-user-id", required = true) @NotBlank(message = "x-user-id header is required") String userId
    ){
        CreateUrlResponseDto createUrlResponseDto = urlService.createShortenUrl(createUrlRequestDto, userId);
        return new ResponseEntity<>(createUrlResponseDto, HttpStatus.CREATED);
    }

}
