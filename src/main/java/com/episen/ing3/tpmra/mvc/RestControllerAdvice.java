package com.episen.ing3.tpmra.mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.episen.ing3.tpmra.exception.BadRequestException;
import com.episen.ing3.tpmra.exception.ConflictException;
import com.episen.ing3.tpmra.exception.ForbiddenException;
import com.episen.ing3.tpmra.exception.NotFoundException;
import com.episen.ing3.tpmra.model.ErrorMessage;
import com.episen.ing3.tpmra.model.ErrorMessageType;
import com.episen.ing3.tpmra.model.ErrorMessages;

import lombok.extern.slf4j.Slf4j;

@EnableWebMvc
@ControllerAdvice
@Slf4j
/**
 * On surcharge cette classe fournie par Spring pour gérer les erreurs à notre convenance
 */
public class RestControllerAdvice extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage =  ErrorMessage.builder().errorMessage(ex.getMessage()).errorCode(BadRequestException.BAD_REQUEST_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(status), errorMessage);
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage =  ErrorMessage.builder().errorMessage(ex.getMessage()).errorCode(BadRequestException.BAD_REQUEST_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(status), errorMessage);
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage =  ErrorMessage.builder().errorMessage(ex.getMessage()).errorCode(BadRequestException.BAD_REQUEST_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(status), errorMessage);
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    /**
     * déclare une Handler spécifique pour nos exceptions fonctionnelles
     **/
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
    	log.info(ex.getErrorMessage().toString());
        ErrorMessage errorMessage =  ErrorMessage.builder().errorMessage(ex.getMessage()).errorCode(BadRequestException.BAD_REQUEST_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(ex.getHttpStatus()), errorMessage);
        return new ResponseEntity<>(errorMessages,ex.getHttpStatus());
    }
    
    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
    	log.info(ex.getErrorMessage().toString());
        ErrorMessage errorMessage =  ErrorMessage.builder().errorMessage(ex.getMessage()).errorCode(ConflictException.CONFLICT_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(ex.getHttpStatus()), errorMessage);
        return new ResponseEntity<>(errorMessages,ex.getHttpStatus());
    }
    
    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, WebRequest request) {
    	log.info(ex.getErrorMessage().toString());
        ErrorMessage errorMessage =  ErrorMessage.builder().errorMessage(ex.getMessage()).errorCode(ForbiddenException.FORBIDDEN_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(ex.getHttpStatus()), errorMessage);
        return new ResponseEntity<>(errorMessages,ex.getHttpStatus());
    }
    
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
    	log.info(ex.getErrorMessage().toString());
        ErrorMessage errorMessage =  ErrorMessage.builder().errorMessage(ex.getMessage()).errorCode(NotFoundException.NOT_FOUND_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(ex.getHttpStatus()), errorMessage);
        return new ResponseEntity<>(errorMessages,ex.getHttpStatus());
    }
    
}
