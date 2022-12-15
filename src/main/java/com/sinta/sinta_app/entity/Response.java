package com.sinta.sinta_app.entity;

import lombok.Data;

@Data
public class Response {
    
    private String message;

    private int code;

    private boolean success;

    private Object data;
}
