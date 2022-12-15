package com.sinta.sinta_app.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.sinta.sinta_app.entity.Response;

@Component
public class ResponseUtil {
    
    public ResponseEntity<Response> sendResponse(String message, HttpStatus code, boolean success, Object data){
        Response response = new Response();
        response.setCode(code.value());
        response.setMessage(message);
        response.setSuccess(success);
        response.setData(data);
        return ResponseEntity.status(code).body(response);
    }
}
