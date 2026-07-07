package com.spring_starter.url_shortner.controller;

import com.spring_starter.url_shortner.dto.createUrlDto.CreateUrlRequestDto;
import com.spring_starter.url_shortner.dto.createUrlDto.CreateUrlResponseDto;
import com.spring_starter.url_shortner.dto.getUrlDto.GetUrlResponseDto;
import com.spring_starter.url_shortner.dto.updateUrlDto.UpdateUrlRequestDto;
import com.spring_starter.url_shortner.dto.updateUrlDto.UpdateUrlResponseDto;
import com.spring_starter.url_shortner.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<Page<GetUrlResponseDto>> getShortUrls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ){
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,sortBy));
        Page<GetUrlResponseDto> urlsResponseDto= urlService.getShortUrls(pageable);
        return new ResponseEntity<>(urlsResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{shortenCode}")
    public ResponseEntity<Boolean> deleteShortUrl(
            @PathVariable(required = true) String shortenCode
    ){
        return new ResponseEntity<>(urlService.deleteShortenCode(shortenCode),HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{shortenCode}")
    public ResponseEntity<UpdateUrlResponseDto> updateShortenedUrlByCode(
            @PathVariable(required = true) String shortenCode,
            @RequestBody @Valid UpdateUrlRequestDto urlRequestDto
    ){
        return new ResponseEntity<>(urlService.updateShortenedUrl(shortenCode,urlRequestDto),HttpStatus.OK);
    }
}
