package com.sinta.sinta_app.app.agent;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinta.sinta_app.dto.agent.RegistrationDto;
import com.sinta.sinta_app.entity.Response;
import com.sinta.sinta_app.entity.agent.Agent;
import com.sinta.sinta_app.exception.AgentNotFoundException;
import com.sinta.sinta_app.util.ResponseUtil;

@Service
@Transactional
public class AgentService {
    
    private final AgentRepository repository;

    private final BCryptPasswordEncoder bcrypt;

    private final ResponseUtil util;

    @Autowired
    public AgentService(AgentRepository repository, BCryptPasswordEncoder bcrypt, ResponseUtil util){
        this.repository = repository;
        this.bcrypt = bcrypt;
        this.util = util;
    }

    public ResponseEntity<Response> createAgent(RegistrationDto dto){
        Agent agent = new Agent();
        agent.setUsername(dto.username());
        agent.setEmail(dto.email());
        agent.setPassword(bcrypt.encode(dto.password()));
        agent.setNamaBadanUsaha(dto.namaBadanUsaha());
        this.repository.save(agent);
        return this.util.sendResponse("sukses membuat agent", HttpStatus.CREATED, true, agent);
    }

    public ResponseEntity<Response> getAgent(Long id) throws AgentNotFoundException{
        Agent agent = this.repository.findById(id).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        return this.util.sendResponse("sukses mendapatkan agent", HttpStatus.OK, true, agent);
    }

    public ResponseEntity<Response> getAgents() throws AgentNotFoundException{
        Iterable<Agent> agent = this.repository.findAll();
        if(agent instanceof List<Agent> list){
            if(list.size() == 0){
                throw new AgentNotFoundException("data agent tidak ditemukan!");
            } else {
                return this.util.sendResponse("sukses mendapatkan agent", HttpStatus.OK, true, list);
            }
        } else {
            return this.util.sendResponse("class cast exception, kesalahan internal server!", HttpStatus.INTERNAL_SERVER_ERROR, false, null);
        }
    }
}
