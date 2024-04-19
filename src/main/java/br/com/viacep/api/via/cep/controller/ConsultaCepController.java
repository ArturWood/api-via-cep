package br.com.viacep.api.via.cep.controller;

import br.com.viacep.api.via.cep.domain.address.dto.AddressResponseDto;
import br.com.viacep.api.via.cep.service.ConsultaCepService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
