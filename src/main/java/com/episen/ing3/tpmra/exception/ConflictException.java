package com.episen.ing3.tpmra.exception;

import org.springframework.http.HttpStatus;

import com.episen.ing3.tpmra.model.ErrorMessage;

@SuppressWarnings("serial")
public class ConflictException extends AbstractDocumentException {
    public static final String CONFLICT_CODE = "err.func.wired.conflict";
    public static final String CONFLICT_MESSAGE =
            "The request could not be completed due to a conflict with the current state of the target resource";

    public static final ConflictException DEFAULT = new ConflictException(CONFLICT_MESSAGE);

    public ConflictException(String message) {
        this(CONFLICT_CODE, message);
    }

    public ConflictException(String code, String message) {
        super(HttpStatus.CONFLICT,
                ErrorMessage.builder()
                        .errorCode(code)
                        .errorMessage(message)
                        .build());
    }
}
