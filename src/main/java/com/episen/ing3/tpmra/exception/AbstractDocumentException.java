package com.episen.ing3.tpmra.exception;

import org.springframework.http.HttpStatus;

import com.episen.ing3.tpmra.model.ErrorMessage;

import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class AbstractDocumentException extends RuntimeException {

    private final transient ErrorMessage errorMessage;
    private final HttpStatus httpStatus;

    public AbstractDocumentException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public AbstractDocumentException(ErrorMessage errorMessage) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
}
