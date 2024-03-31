package com.todolist.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todolist.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ValidacaoCamposHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tratarValidacoes(MethodArgumentNotValidException ex) {


        List<Map<String, String>> listErros = ex.getBindingResult ()
                .getFieldErrors ()
                .stream ()
                .map (error -> {

                    Map<String, String> erros = new HashMap<> ();
                    erros.put ("campo", obterNomePropriedade (error));
                    erros.put ("descricao", error.getDefaultMessage ());

                    return erros;
                })
                .toList ();


        ErrorResponse response = ErrorResponse.builder ()
                .status (HttpStatus.BAD_REQUEST.toString ())
                .erros (listErros)
                .build ();



        return new ResponseEntity<> (response, HttpStatus.BAD_REQUEST);

    }


    public String obterNomePropriedade(final FieldError error) {

        if(error.contains (ConstraintViolation.class)) {

            try {

                final ConstraintViolation<?> violation = error.unwrap (ConstraintViolation.class);
                final Field campo;

                campo = violation.getRootBeanClass ().getDeclaredField (error.getField ());

                final JsonProperty anotacao = campo.getAnnotation (JsonProperty.class);

                if(anotacao != null && anotacao.value () != null && !anotacao.value ().isEmpty ()) {
                    return anotacao.value ();
                }
            } catch (Exception e) {
            }
        }

        return error.getField ();
    }





}
