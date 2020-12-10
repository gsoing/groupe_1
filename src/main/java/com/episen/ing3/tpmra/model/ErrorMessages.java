package com.episen.ing3.tpmra.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@SuppressWarnings("serial")
public class ErrorMessages implements Serializable {
	
    private ErrorMessageType type;
    private List<ErrorMessage> messages;

    public ErrorMessages(ErrorMessageType type, ErrorMessage...errorMessages){
            this.type = type;
            this.messages = Arrays.asList(errorMessages);
    }
}
