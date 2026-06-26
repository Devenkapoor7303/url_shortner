package com.spring_starter.url_shortner.dto.createUrlDto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUrlRequestDto {

    @NotBlank(message = "longUrl is required")
    private String longUrl;

    @Size(min = 5 , max = 15, message = "size of custom alias can be between 5 and 15")
    private String customAlias;

    @Future(message = "expiry date can be future")
    private LocalDateTime expiryDate;
}
