package com.spring_starter.url_shortner.mapper;

import com.spring_starter.url_shortner.dto.createUrlDto.CreateUrlResponseDto;
import com.spring_starter.url_shortner.dto.getUrlDto.GetUrlResponseDto;
import com.spring_starter.url_shortner.dto.updateUrlDto.UpdateUrlRequestDto;
import com.spring_starter.url_shortner.dto.updateUrlDto.UpdateUrlResponseDto;
import com.spring_starter.url_shortner.entity.Url;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UrlMapper {

    @Mapping(target = "shortenUrl", expression = "java(baseUrl + \"/api/v1/url/\" + url.getShortenCode())")
    @Mapping(target = "active", source = "url.active")
    CreateUrlResponseDto toCreateResponseDto(Url url, String baseUrl);

    GetUrlResponseDto toGetResponseDto(Url url);

    UpdateUrlResponseDto toUpdateResponseDto(Url url);

    void updateEntityFromDto(UpdateUrlRequestDto dto, @MappingTarget Url url);
}
