package com.frankmoley.services.pii.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Frank Moley
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason="A conflict exists in the request")
public class RequestConflictException extends RuntimeException {
    public RequestConflictException() {
        super();
    }

    public RequestConflictException(String message) {
        super(message);
    }

    public RequestConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestConflictException(Throwable cause) {
        super(cause);
    }
}
