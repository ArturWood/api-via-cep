package br.com.viacep.api.via.cep.service;

import br.com.viacep.api.via.cep.domain.address.dto.AddressResponseDto;
import br.com.viacep.api.via.cep.domain.address.dto.AddressResultDto;
import br.com.viacep.api.via.cep.domain.addresses.dto.AddressesRequestDto;
import br.com.viacep.api.via.cep.infra.exception.AddressNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Test
    @DisplayName("Deveria retornar uma lista de AddressResponseDto com um CEPs validos")
    void scenario04() {
        // Arrange
        List<AddressesRequestDto> dtos = Arrays.asList(new AddressesRequestDto("01001000"), new AddressesRequestDto("87654321"));
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
        when(restTemplate.getForEntity(any(String.class), eq(AddressResultDto.class))).thenReturn(responseEntity);

        // Act
        List<AddressResponseDto> addresses = consultaCepService.getAddresses(dtos);

        // Assert
        assertEquals(2, addresses.size());
        assertEquals("Praça da Sé", addresses.get(0).logradouro());
    }

    @Test
    @DisplayName("Deveria retornar uma lista de AddressResponseDto indicando CEPs invalidos")
    void scenario05() {
        // Arrange
        List<AddressesRequestDto> dtos = Arrays.asList(new AddressesRequestDto("12345678"), new AddressesRequestDto("87654321"));
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
        when(restTemplate.getForEntity(any(String.class), eq(AddressResultDto.class))).thenReturn(responseEntity);

        // Act
        List<AddressResponseDto> addresses = consultaCepService.getAddresses(dtos);

        // Assert
        assertEquals(2, addresses.size());
        assertEquals("Não foi possivel localizar o endereço do CEP: 12345678", addresses.get(0).logradouro());
    }

    @Test
    @DisplayName("Deveria retornar uma lista de AddressResponseDto com um CEP valido e não validos")
    void scenario06() {
        // Arrange
        List<AddressesRequestDto> dtos = Arrays.asList(new AddressesRequestDto("01001000"), new AddressesRequestDto("87654321"));
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
        when(restTemplate.getForEntity(any(String.class), eq(AddressResultDto.class))).thenReturn(responseEntity);

        // Act
        List<AddressResponseDto> addresses = consultaCepService.getAddresses(dtos);

        // Assert
        assertEquals(2, addresses.size());
        assertEquals("Praça da Sé", addresses.get(0).logradouro());
        assertEquals("Não foi possivel localizar o endereço do CEP: 87654321", addresses.get(1).logradouro());
    }
}
