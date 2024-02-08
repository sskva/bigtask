package ru.sskva.bigtask.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sskva.bigtask.config.Config;
import ru.sskva.bigtask.domain.response.Response;
import ru.sskva.bigtask.domain.response.error.Error;
import ru.sskva.bigtask.domain.response.error.ErrorResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    private final Config config;

    @ExceptionHandler(ExistsFileInProcessOrLoadingException.class)
    public ResponseEntity<Response> existsFileInProgressException(ExistsFileInProcessOrLoadingException ex) {
        log.error("existsFileInProgressException: {}", ex.toString());
        return buildErrorResponse(BAD_REQUEST, "Дождитесь обработки предыдущего файла");
    }

    @ExceptionHandler(WrongCountLinesException.class)
    public ResponseEntity<Response> wrongCountLinesException(WrongCountLinesException ex) {
        log.error("wrongCountLinesException: {}", ex.toString());
        String message = "Количество строк в файле должно быть от "
                .concat(String.valueOf(config.getMinCountLines()))
                .concat(" до ")
                .concat(String.valueOf(config.getMaxCountLines()))
                .concat(" строк");
        return buildErrorResponse(BAD_REQUEST, message);
    }

    private ResponseEntity<Response> buildErrorResponse(HttpStatus status, String message) {
        log.error("httpStatus: {}, message: {}", status, message);
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().userMessage(message).build()).build(), status);
    }
}
