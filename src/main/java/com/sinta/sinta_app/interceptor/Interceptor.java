package com.sinta.sinta_app.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sinta.sinta_app.entity.Response;
import com.sinta.sinta_app.exception.AgentNotFoundException;
import com.sinta.sinta_app.exception.PortofolioNotFoundException;
import com.sinta.sinta_app.exception.TripNotFoundException;
import com.sinta.sinta_app.util.ResponseUtil;

@RestControllerAdvice
public class Interceptor {

    private final ResponseUtil util;

    @Autowired
    public Interceptor(ResponseUtil util){
        this.util = util;
    }
    
    @ExceptionHandler(value = AgentNotFoundException.class)
    public ResponseEntity<Response> handleException(AgentNotFoundException ex){
        return this.util.sendResponse(ex.getMessage(), HttpStatus.NOT_FOUND, false, null);
    }

    @ExceptionHandler(value = TripNotFoundException.class)
    public ResponseEntity<Response> handleException(TripNotFoundException ex){
        return this.util.sendResponse(ex.getMessage(), HttpStatus.NOT_FOUND, false, null);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleException(MethodArgumentNotValidException ex){
        return this.util.sendResponse(ex.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false, null);
    }

    @ExceptionHandler(value = PortofolioNotFoundException.class)
    public ResponseEntity<Response> handleException(PortofolioNotFoundException ex){
        return this.util.sendResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, false, null);
    }
}
