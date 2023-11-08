package dev.berkanterdogan.bucket4j.demo.ratelimiting.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class RateLimitException extends RuntimeException {

    private final HttpStatusCode httpStatusCode = HttpStatus.TOO_MANY_REQUESTS;

    public RateLimitException() {
        super();
    }

    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
