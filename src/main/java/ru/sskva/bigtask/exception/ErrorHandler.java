package ru.sskva.bigtask.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sskva.bigtask.domain.response.Response;
import ru.sskva.bigtask.domain.response.error.Error;
import ru.sskva.bigtask.domain.response.error.ErrorResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(ExistsFileInProgressException.class)
    public ResponseEntity<Response> existsFileInProgressException(ExistsFileInProgressException ex) {
        log.error("existsFileInProgressException: {}", ex.toString());
        return buildErrorResponse(BAD_REQUEST, "Дождитесь обработки предыдущего файла");
    }


    private ResponseEntity<Response> buildErrorResponse(HttpStatus status, String message) {
        log.error("httpStatus: {}, message: {}", status, message);
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().userMessage(message).build()).build(), status);
    }
}
