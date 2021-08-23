package com.fieldwire.floorplan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.fieldwire.floorplan.util.GlobalConstants;

@ControllerAdvice  
@RestController  
public class ExceptionHandlingController {
	
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionResponse> invalidRequest(InvalidRequestException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setReturnCode(""+HttpStatus.BAD_REQUEST);
        response.setReturnMessage(ex.getMessage());              
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordsNotFoundException.class)
    public ResponseEntity<ExceptionResponse> invalidRequest(RecordsNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setReturnCode(""+HttpStatus.NOT_FOUND);
        response.setReturnMessage(ex.getMessage()); 
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);        
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> genericException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setReturnCode(GlobalConstants.FATAL_ERROR_CODE);
        ex.printStackTrace();
        response.setReturnMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }          



}
