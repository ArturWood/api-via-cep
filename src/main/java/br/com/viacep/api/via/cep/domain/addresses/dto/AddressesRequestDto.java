package br.com.viacep.api.via.cep.domain.addresses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressesRequestDto(
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep
) {
}
