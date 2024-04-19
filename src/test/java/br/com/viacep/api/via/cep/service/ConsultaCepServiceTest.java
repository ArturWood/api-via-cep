package br.com.viacep.api.via.cep.service;

import br.com.viacep.api.via.cep.domain.address.dto.AddressResponseDto;
import br.com.viacep.api.via.cep.domain.address.dto.AddressResultDto;
import br.com.viacep.api.via.cep.infra.exception.AddressNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsultaCepServiceTest {
    private ConsultaCepService consultaCepService = new ConsultaCepService();

    @Test
    @DisplayName("Deveria retornar um AddressResponseDto valido com um CEP valido")
    void scenario01() {
        // Arrange
        String cep = "01001000";
        String url = "https://viacep.com.br/ws/" + cep + "/json";
        AddressResultDto addressResultDto = new AddressResultDto(
                "01001-000",
                "Praça da Sé",
                "lado ímpar",
                "Sé",
                "São Paulo",
                "SP",
                "3550308",
                "1004",
                "11",
                "7107"
        );
        ResponseEntity<AddressResultDto> responseEntity = new ResponseEntity<>(addressResultDto, HttpStatus.OK);

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForEntity(url, AddressResultDto.class)).thenReturn(responseEntity);

        // Act
        AddressResponseDto addressResponseDto = consultaCepService.getAddress(cep);

        // Assert
        assertNotNull(addressResponseDto);
        assertEquals("Praça da Sé", addressResponseDto.logradouro());
    }

    @Test
    @DisplayName("Deveria lançar AddressNotFoundException com um CEP valido que volta com o logradouro null")
    void scenario02() {
        // Arrange
        String cep = "12345678";
        String url = "https://viacep.com.br/ws/" + cep + "/json";
        AddressResultDto addressResultDto = new AddressResultDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        ResponseEntity<AddressResultDto> responseEntity = new ResponseEntity<>(addressResultDto, HttpStatus.OK);

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForEntity(url, AddressResultDto.class)).thenReturn(responseEntity);

        // Act + Assert
        assertThrows(AddressNotFoundException.class, () -> consultaCepService.getAddress(cep));
    }

    @Test
    @DisplayName("Deveria devolver Bad Request com um CEP invalido")
    void scenario03() {
        // Arrange
        String cep = "1234";

        // Act + Assert
        assertThrows(HttpClientErrorException.class, () -> consultaCepService.getAddress(cep));
    }
}
