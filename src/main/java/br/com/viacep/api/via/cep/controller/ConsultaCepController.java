package br.com.viacep.api.via.cep.controller;

import br.com.viacep.api.via.cep.domain.address.dto.AddressResponseDto;
import br.com.viacep.api.via.cep.domain.addresses.dto.AddressesRequestDto;
import br.com.viacep.api.via.cep.service.ConsultaCepService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta-cep")
public class ConsultaCepController {
    @Autowired
    private ConsultaCepService consultaCepService;

    @GetMapping("{cep}")
    public ResponseEntity<AddressResponseDto> getCep(@PathVariable @Pattern(regexp = "\\d{8}") String cep) {
        AddressResponseDto address = consultaCepService.getAddress(cep);
        return ResponseEntity.ok(address);
    }

    @PostMapping
    public ResponseEntity<List<AddressResponseDto>> getAddresses(@RequestBody @Valid List<AddressesRequestDto> dto) {
        List<AddressResponseDto> addresses = consultaCepService.getAddresses(dto);
        return ResponseEntity.ok(addresses);
    }
}
