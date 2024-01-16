package net.fribbtastic.learningSpringBoot.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Wrapper to include the Error Details in the Response
 *
 * @author Frederic Eßer
 */
@Getter
public class ErrorResponse {

    /**
     * The detailed information about the error
     */
    @JsonProperty("error")
    private final ErrorDetails errorDetails;

    /**
     * Construct the ErrorResponse
     *
     * @param status - the {@link HttpStatus} element
     * @param message - the message of the error
     * @param details - the detailed information about the error
     */
    public ErrorResponse(HttpStatus status, String message, String details) {
        this.errorDetails = new ErrorDetails(status, message, details);
    }

}
