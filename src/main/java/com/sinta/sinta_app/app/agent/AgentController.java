package com.sinta.sinta_app.app.agent;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sinta.sinta_app.dto.agent.CompleteDataDto;
import com.sinta.sinta_app.dto.agent.LoginDto;
import com.sinta.sinta_app.dto.agent.RegistrationDto;
import com.sinta.sinta_app.dto.agent.UpdateAgentDto;
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

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginDto dto) throws AgentNotFoundException{
        return this.service.login(dto);
    }

    @PostMapping("/completedata/{idAgent}")
    public ResponseEntity<Response> completeData(@PathVariable("idAgent") Long idAgent, @RequestPart("data") @Valid CompleteDataDto dto, @RequestPart("surat_izin_usaha") MultipartFile file) throws AgentNotFoundException, IOException{
        return this.service.completeData(idAgent, dto, file);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getAgent(@PathVariable("id") Long id) throws AgentNotFoundException{
        return this.service.getAgent(id);
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getAgent() throws AgentNotFoundException{
        return this.service.getAgents();
    }

    @GetMapping("/isverified")
    public ResponseEntity<Response> getVerified(@RequestParam("idAgent") Long id) throws AgentNotFoundException{
        return this.service.isVerified(id);
    }

    @PatchMapping("/update/{agentId}")
    public ResponseEntity<Response> updateAgent(@PathVariable("agentId") Long id,  @RequestBody @Valid UpdateAgentDto dto) throws AgentNotFoundException{
        return this.service.updateAgent(id, dto);
    }
}
