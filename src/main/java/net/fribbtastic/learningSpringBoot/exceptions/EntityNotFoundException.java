package net.fribbtastic.learningSpringBoot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Frederic Eßer
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    /**
     * Exception to handle Entities not being found in the database
     *
     * @param id - the ID of the Entity
     */
    public EntityNotFoundException(Long id) {
        super("Entity with the ID '" + id + "' could not be found");
    }
}
