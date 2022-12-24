package com.sinta.sinta_app.app.portofolio;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sinta.sinta_app.app.agent.AgentRepository;
import com.sinta.sinta_app.dto.portofolio.PortofolioDto;
import com.sinta.sinta_app.entity.Response;
import com.sinta.sinta_app.entity.agent.Agent;
import com.sinta.sinta_app.entity.portofolio.Portofolio;
import com.sinta.sinta_app.exception.AgentNotFoundException;
import com.sinta.sinta_app.exception.PortofolioNotFoundException;
import com.sinta.sinta_app.util.ResponseUtil;

@Service
@Transactional
public class PortofolioService {
    
    private final PortofolioRepository repo;

    private final ResponseUtil util;

    private final AgentRepository agentRepository;

    @Autowired
    public PortofolioService(PortofolioRepository repo, ResponseUtil util, AgentRepository agentRepository) {
        this.repo = repo;
        this.util = util;
        this.agentRepository = agentRepository;
    }

    public ResponseEntity<Response> createPortofolio(Long idAgent, PortofolioDto dto, MultipartFile file) throws AgentNotFoundException, IOException{
        Agent agent = this.agentRepository.findById(idAgent).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        Portofolio portofolio = new Portofolio();
        portofolio.setJudulPortofolio(dto.judulPortofolio());
        portofolio.setLinkPaketWisata(dto.linkPaketWisata());
        portofolio.setDeskripsiPortofolio(dto.deskripsiPortofolio());
        portofolio.setFoto(file.getBytes());
        portofolio.setAgent(agent);
        this.repo.save(portofolio);
        return this.util.sendResponse("sukses membuat portofolio", HttpStatus.CREATED, true, portofolio);
    }

    public ResponseEntity<Response> updatePortofolio(Long idAgent, Long idPortofolio, PortofolioDto dto, MultipartFile file) throws AgentNotFoundException, PortofolioNotFoundException, IOException{
        this.agentRepository.findById(idAgent).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        Portofolio portofolio = this.repo.find(idAgent, idPortofolio).orElseThrow(() -> new PortofolioNotFoundException("data portofolio tidak ditemukan"));
        portofolio.setDeskripsiPortofolio(dto.deskripsiPortofolio());
        portofolio.setJudulPortofolio(dto.judulPortofolio());
        portofolio.setLinkPaketWisata(dto.linkPaketWisata());
        portofolio.setFoto(file.getBytes());
        this.repo.save(portofolio);
        return this.util.sendResponse("sukses mengupdate portofolio", HttpStatus.OK, true, portofolio);
    }

    public ResponseEntity<Response> getPortofolios(Long idAgent) throws AgentNotFoundException{
        this.agentRepository.findById(idAgent).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        List<Portofolio> list = this.repo.find(idAgent);
        if(list.size() == 0){
            return this.util.sendResponse("data portofolio tidak ada", HttpStatus.INTERNAL_SERVER_ERROR, false, null);
        } else {
            return this.util.sendResponse("sukses menemukan portofolio", HttpStatus.OK, true, list);
        }
    }

    public ResponseEntity<Response> getPortofolio(Long idAgent, Long idPortofolio) throws AgentNotFoundException, PortofolioNotFoundException{
        this.agentRepository.findById(idAgent).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        Portofolio portofolio = this.repo.find(idAgent, idPortofolio).orElseThrow(() -> new PortofolioNotFoundException("data portofolio tidak ditemukan"));
        return this.util.sendResponse("sukses menemukan portofolio", HttpStatus.OK, true, portofolio);
    }
}
