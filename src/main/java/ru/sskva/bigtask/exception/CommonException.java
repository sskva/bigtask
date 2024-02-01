package ru.sskva.bigtask.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.sskva.bigtask.domain.constant.Code;

@Data
@Builder
public class CommonException extends RuntimeException {

    private final Code code;
    private final String userMessage;
    private final String techMessage;
    private final HttpStatus httpStatus;
}
