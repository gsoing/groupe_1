package com.episen.ing3.tpmra.exception;

import org.springframework.http.HttpStatus;

import com.episen.ing3.tpmra.model.ErrorMessage;

@SuppressWarnings("serial")
public class ForbiddenException extends AbstractDocumentException {

    public static final ForbiddenException DEFAULT = new ForbiddenException();

    public static final String FORBIDDEN_CODE = "err.func.wired.forbidden";
    public static final String FORBIDDEN_MESSAGE = "The access is forbidden";

    private ForbiddenException() {
        super(HttpStatus.FORBIDDEN,
                ErrorMessage.builder()
                		.code(FORBIDDEN_CODE)
                        .message(FORBIDDEN_MESSAGE)
                        .build());
    }
}
