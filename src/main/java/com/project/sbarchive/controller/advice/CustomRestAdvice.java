package com.project.sbarchive.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {

    // BindException 오류 시 처리하는 메소드
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleBindException(BindException e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        if(e.hasErrors()) {
            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }
        return ResponseEntity.badRequest().body(errorMap);

    }

    // 존재하지 않는 FK키 입력 시 에러를 표시할 설정 메소드
    // Swagger-ui 에서 에러메시지를 json 형태로 받음
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handlerFKException(Exception e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", "" + System.currentTimeMillis());
        errorMap.put("msg", "constraint fails");

        return ResponseEntity.badRequest().body(errorMap);
    }

    // 존재하지 않는 정보를 호출했을 시 에러를 표시할 설정 메소드
    // Swagger-ui 에서 에러메시지를 json 형태로 받음
    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handlerNoSuchElementException(Exception e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", "" + System.currentTimeMillis());
        errorMap.put("msg", "No Such Element Exception");

        return ResponseEntity.badRequest().body(errorMap);

    }

    // 존재하지 않는 정보를 호출했을 시 에러를 표시할 설정 메소드
    // Swagger-ui 에서 에러메시지를 json 형태로 받음
    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handlerEmptyResultDataAccessExceptionException(Exception e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", "" + System.currentTimeMillis());
        errorMap.put("msg", "Empty Result Data Access Exception");

        return ResponseEntity.badRequest().body(errorMap);

    }

}
