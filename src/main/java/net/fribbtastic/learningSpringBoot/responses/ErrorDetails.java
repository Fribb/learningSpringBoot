package net.fribbtastic.learningSpringBoot.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

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
     * The Exception Type
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)          // We don't want to display the type when it is null
    private final String type;
    /**
     * The ReasonPhrase of the HTTP Status
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
     * @param message - The Message of the Error
     * @param details - The detailed description of the Error
     */
    public ErrorDetails(String message, String details, String type) {
        this.message = message;
        this.details = details;
        this.type = type;
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
