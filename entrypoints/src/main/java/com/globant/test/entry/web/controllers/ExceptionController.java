package com.globant.test.entry.web.controllers;

import com.globant.test.entry.web.controllers.dto.HttpError;
import com.globant.test.exceptions.CelebrityNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class ExceptionController {

    /**
     * @param e
     * @return
     */
    @ExceptionHandler(CelebrityNotFound.class)
    public ResponseEntity<HttpError> notFoundException(final CelebrityNotFound e) {
        return error(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * @param exception
     * @param httpStatus
     * @param logRef
     * @return
     */
    private ResponseEntity<HttpError> error(final Exception exception, final HttpStatus httpStatus,
                                            final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new HttpError(logRef, message), httpStatus);
    }
}
