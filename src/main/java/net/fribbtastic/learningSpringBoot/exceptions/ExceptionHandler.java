package net.fribbtastic.learningSpringBoot.exceptions;

import net.fribbtastic.learningSpringBoot.responses.ApiResponse;
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
     * Method to handle {@link EntityNotFoundException}s and Respond with an {@link ApiResponse}
     *
     * @param exception - the {@link EntityNotFoundException}
     * @return a {@link ResponseEntity} with the 404 Status code and the {@link ApiResponse} as Body
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(EntityNotFoundException exception) {

        ApiResponse<?> response = ApiResponse.createFailureResponse(HttpStatus.NOT_FOUND, "Entity not found", exception);     // create a failed API Response

        return ResponseEntity                                                                                               // return the ResponseEntity wrapper
                .status(HttpStatus.NOT_FOUND)                                                                               // set the status of the Response to 404 Not Found
                .body(response);                                                                                            // set the body to the ApiResponse
    }
}
