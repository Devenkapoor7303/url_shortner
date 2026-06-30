package com.spring_starter.url_shortner.controller;

import com.spring_starter.url_shortner.dto.createUrlDto.CreateUrlRequestDto;
import com.spring_starter.url_shortner.dto.createUrlDto.CreateUrlResponseDto;
import com.spring_starter.url_shortner.dto.getUrlDto.GetUrlResponseDto;
import com.spring_starter.url_shortner.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/url")
public class UrlController {

    private final UrlService urlService;

    @PostMapping()
    public ResponseEntity<CreateUrlResponseDto> createShortUrl(
            @RequestBody @Valid CreateUrlRequestDto createUrlRequestDto,
            @RequestHeader(value = "x-buser-id", required = true)  String userId
    ){
        CreateUrlResponseDto createUrlResponseDto = urlService.createShortenUrl(createUrlRequestDto, userId);
        return new ResponseEntity<>(createUrlResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{shortenCode}")
    public ResponseEntity<Void> getShortUrl(
            @PathVariable(required = true) String shortenCode
    ){
        String longUrl = urlService.getLongUrl(shortenCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }

    @GetMapping()
    public ResponseEntity<List<GetUrlResponseDto>> getShortUrls(){
        List<GetUrlResponseDto> urlsResponseDto= urlService.getShortUrls();
        return new ResponseEntity<>(urlsResponseDto,HttpStatus.OK);
    }

}
