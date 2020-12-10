package com.episen.ing3.tpmra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Builder
@Data
@AllArgsConstructor
@SuppressWarnings("serial")
public class ErrorMessage implements Serializable {

    private String code;
    private String message;

}
