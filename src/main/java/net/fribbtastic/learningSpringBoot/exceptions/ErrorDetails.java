package net.fribbtastic.learningSpringBoot.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to define a general Error Details as Response Element
 *
 * @author Frederic Eßer
 */
@Getter
public class ErrorDetails {

    /**
     * The HTTP Code of the Status
     */
    private final int code;
    /**
     * The ReasonPhrase of the HTTP Status
     */
    @JsonProperty("reason")
    private final String reasonPhrase;
    /**
     * The short descriptive message of the error
     */
    private final String message;
    /**
     * The more detailed description of the error
     */
    private final String details;
    /**
     * the timestamp the error happened
     */
    private final String timestamp;

    /**
     * Construct an ErrorDetails element by setting the Status Code, the Reason, the message and details and add the current timestamp
     *
     * @param status - The {@link HttpStatus} that should be used
     * @param message - The Message of the Error
     * @param details - The detailed description of the Error
     */
    public ErrorDetails(HttpStatus status, String message, String details) {
        this.code = status.value();
        this.reasonPhrase = status.getReasonPhrase();
        this.message = message;
        this.details = details;
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
