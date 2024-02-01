package ru.sskva.bigtask.domain.response.error;

import lombok.Builder;
import lombok.Data;
import ru.sskva.bigtask.domain.response.Response;


@Data
@Builder
public class ErrorResponse implements Response {

    private Error error;
}
