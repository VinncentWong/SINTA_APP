package com.sinta.sinta_app.app.agent;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinta.sinta_app.dto.agent.RegistrationDto;
import com.sinta.sinta_app.entity.Response;
import com.sinta.sinta_app.exception.AgentNotFoundException;

@RestController
@RequestMapping("/agent")
public class AgentController {
    
    private final AgentService service;

    public AgentController(AgentService service){
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createAgent(@RequestBody @Valid RegistrationDto dto){
        return this.service.createAgent(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getAgent(@PathVariable("id") Long id) throws AgentNotFoundException{
        return this.service.getAgent(id);
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getAgent() throws AgentNotFoundException{
        return this.service.getAgents();
    }
}
