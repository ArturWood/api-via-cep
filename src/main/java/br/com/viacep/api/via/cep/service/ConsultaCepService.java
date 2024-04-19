package br.com.viacep.api.via.cep.service;

import br.com.viacep.api.via.cep.domain.address.dto.AddressResponseDto;
import br.com.viacep.api.via.cep.domain.address.dto.AddressResultDto;
import br.com.viacep.api.via.cep.domain.addresses.dto.AddressesRequestDto;
import br.com.viacep.api.via.cep.infra.exception.AddressNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
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

    public List<AddressResponseDto> getAddresses(List<AddressesRequestDto> dto) {
        List<AddressResponseDto> addresses = new ArrayList<>();
        for (int i = 0; i < dto.size(); i++) {
            String editedUrl = editUrl(dto.get(i).cep());
            ResponseEntity<AddressResultDto> response = restTemplate.getForEntity(editedUrl, AddressResultDto.class);
            if (response.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(response.getBody()).logradouro() != null) {
                addresses.add(new AddressResponseDto(response.getBody()));
            } else addresses.add(new AddressResponseDto("Não foi possivel localizar o endereço do CEP: " + dto.get(i).cep()));
        }
        return addresses;
    }

    private String editUrl(String cep) {
        return URL + cep + "/json";
    }
}
