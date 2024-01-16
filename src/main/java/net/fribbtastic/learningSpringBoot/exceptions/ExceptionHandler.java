package net.fribbtastic.learningSpringBoot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Class to handle possible Exceptions
 *
 * @author Frederic Eßer
 */
@RestControllerAdvice
public class ExceptionHandler {

    /**
     * Method to handle {@link EntityNotFoundException}s and Respond with an {@link ErrorResponse}
     *
     * @param exception - the {@link EntityNotFoundException}
     * @return a {@link ResponseEntity} with the 404 Status code and the {@link ErrorResponse} as Body
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(EntityNotFoundException exception) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,"Entity not found", exception.getMessage());                  // Create a new ErrorResponse Record

        return ResponseEntity                                                                                           // Return a ResponseEntity
                .status(HttpStatus.NOT_FOUND)                                                                           // Set the status to 404 Not Found
                .body(errorResponse);                                                                                   // Set the body to the ErrorResponse
    }
}
