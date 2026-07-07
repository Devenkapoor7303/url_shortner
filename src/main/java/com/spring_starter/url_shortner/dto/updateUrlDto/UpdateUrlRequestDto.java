package com.spring_starter.url_shortner.dto.updateUrlDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUrlRequestDto {
    @NotBlank(message = "longUrl cannot be blank in a full update")
    @Size(max = 2048, message = "longUrl cannot exceed 2048 characters")
    private String longUrl;

    private String customAlias;

    @FutureOrPresent(message = "Expiry date must be a future or present date/time")
    private LocalDateTime expiryDate;

    @NotNull(message = "isActive state must be explicitly specified (true or false)")
    @JsonProperty("active")
    private Boolean active;
}
