package br.com.viacep.api.via.cep.domain.address.dto;

public record AddressResponseDto(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf,
        String ddd
) {
    public AddressResponseDto(AddressResultDto response) {
        this(response.cep(), response.logradouro(), response.bairro(), response.localidade(), response.uf(), response.ddd());
    }

    public AddressResponseDto(String message) {
        this(null, message, null, null, null, null);
    }
}
