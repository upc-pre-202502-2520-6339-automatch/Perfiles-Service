package automach.profiles.interfaces.rest.errors;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

/**
 * Payload estándar de error para las respuestas REST.
 */
@Getter
@Builder
public class ApiError {

    private final OffsetDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}
