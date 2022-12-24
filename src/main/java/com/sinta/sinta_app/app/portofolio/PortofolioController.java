package com.sinta.sinta_app.app.portofolio;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sinta.sinta_app.dto.portofolio.PortofolioDto;
import com.sinta.sinta_app.entity.Response;
import com.sinta.sinta_app.exception.AgentNotFoundException;
import com.sinta.sinta_app.exception.PortofolioNotFoundException;

@RestController
@RequestMapping("/portofolio")
public class PortofolioController {
    
    private final PortofolioService service;

    @Autowired
    public PortofolioController(PortofolioService service){
        this.service = service;
    }

    @PostMapping("/create/{agentId}")
    public ResponseEntity<Response> createPortofolio(@PathVariable("agentId") Long agentId, @RequestPart("data") @Valid PortofolioDto dto, @RequestPart("photo") MultipartFile file) throws AgentNotFoundException, IOException{
        return this.service.createPortofolio(agentId, dto, file);
    }

    @GetMapping("/get/{agentId}")
    public ResponseEntity<Response> getPortofolios(@PathVariable("agentId") Long agentId) throws AgentNotFoundException{
        return this.service.getPortofolios(agentId);
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getPortofolio(@RequestParam("agentId") Long agentId, @RequestParam("portofolioId") Long portofolioId) throws AgentNotFoundException, PortofolioNotFoundException{
        return this.service.getPortofolio(agentId, portofolioId);
    }

    @PatchMapping("/update")
    public ResponseEntity<Response> updatePortofolio(@RequestParam("agentId") Long agentId, @RequestParam("portofolioId") Long portofolioId, @RequestPart("data") @Valid PortofolioDto dto, @RequestPart("photo") MultipartFile file) throws AgentNotFoundException, IOException, PortofolioNotFoundException{
        return this.service.updatePortofolio(agentId, portofolioId, dto, file);
    }
}
