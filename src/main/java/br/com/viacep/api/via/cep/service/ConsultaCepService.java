package br.com.viacep.api.via.cep.service;

import br.com.viacep.api.via.cep.domain.address.dto.AddressResponseDto;
import br.com.viacep.api.via.cep.domain.address.dto.AddressResultDto;
import br.com.viacep.api.via.cep.infra.exception.AddressNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ConsultaCepService {
    private final String URL = "https://viacep.com.br/ws/";
    private final RestTemplate restTemplate = new RestTemplate();

    public AddressResponseDto getAddress(String cep) {
        String editedUrl = editUrl(cep);
        ResponseEntity<AddressResultDto> response = restTemplate.getForEntity(editedUrl, AddressResultDto.class);
        if (response.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(response.getBody()).logradouro() != null) {
            return new AddressResponseDto(response.getBody());
        } else throw new AddressNotFoundException("CEP não localizado");
    }

    private String editUrl(String cep) {
        return URL + cep + "/json";
    }
}
