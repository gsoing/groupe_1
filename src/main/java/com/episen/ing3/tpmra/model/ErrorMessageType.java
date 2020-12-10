package com.episen.ing3.tpmra.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * The type of message returned.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorMessageType {
	
    TECHNICAL(HttpStatus.INTERNAL_SERVER_ERROR),
    FUNCTIONAL(HttpStatus.BAD_REQUEST);

    private final HttpStatus defaultStatus;

    public static ErrorMessageType fromStatus(HttpStatus status) {
        if (status.is4xxClientError()) {
            return FUNCTIONAL;
        } else if (status.is5xxServerError()) {
            return TECHNICAL;
        }
        throw new IllegalArgumentException("HTTP status '" + status + "' is not a valid exception status.");
    }
}
