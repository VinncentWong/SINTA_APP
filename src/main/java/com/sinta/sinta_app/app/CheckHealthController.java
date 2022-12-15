package com.sinta.sinta_app.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckHealthController {
    
    @GetMapping("/check")
    public String check(){
        return "OK";
    }
}
