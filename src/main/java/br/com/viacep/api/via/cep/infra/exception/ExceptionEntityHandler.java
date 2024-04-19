package br.com.viacep.api.via.cep.infra.exception;

import br.com.viacep.api.via.cep.infra.exception.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity handlerAddressNotFound(AddressNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDto> handlerMethodValidation() {
        return ResponseEntity.badRequest().body(new ErrorResponseDto("Formato inválido, o CEP precisa ser composto por 8 numeros"));
    }
}
