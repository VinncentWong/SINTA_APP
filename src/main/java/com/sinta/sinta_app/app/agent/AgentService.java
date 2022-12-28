package com.sinta.sinta_app.app.agent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sinta.sinta_app.app.trip.TripRepository;
import com.sinta.sinta_app.dto.agent.CompleteDataDto;
import com.sinta.sinta_app.dto.agent.LoginDto;
import com.sinta.sinta_app.dto.agent.RegistrationDto;
import com.sinta.sinta_app.dto.agent.UpdateAgentDto;
import com.sinta.sinta_app.entity.Response;
import com.sinta.sinta_app.entity.StatusVerified;
import com.sinta.sinta_app.entity.agent.Agent;
import com.sinta.sinta_app.entity.trip.Trip;
import com.sinta.sinta_app.exception.AgentNotFoundException;
import com.sinta.sinta_app.exception.TripNotFoundException;
import com.sinta.sinta_app.util.JwtUtil;
import com.sinta.sinta_app.util.ResponseUtil;

@Service
@Transactional
public class AgentService {
    
    private final AgentRepository repository;

    private final BCryptPasswordEncoder bcrypt;

    private final ResponseUtil util;
    
    private final JwtUtil jwt;

    private final TripRepository tripRepository;

    @Autowired
    public AgentService(AgentRepository repository, BCryptPasswordEncoder bcrypt, ResponseUtil util, JwtUtil jwt, TripRepository tripRepo){
        this.repository = repository;
        this.bcrypt = bcrypt;
        this.util = util;
        this.jwt = jwt;
        this.tripRepository = tripRepo;
    }

    public ResponseEntity<Response> createAgent(RegistrationDto dto){
        Optional<Agent> check = this.repository.findByEmail(dto.email());
        if(check.isPresent()){
            return this.util.sendResponse("email sudah terdaftar", HttpStatus.INTERNAL_SERVER_ERROR, false, null);
        }
        Agent agent = new Agent();
        agent.setUsername(dto.username());
        agent.setEmail(dto.email());
        agent.setPassword(bcrypt.encode(dto.password()));
        agent.setNamaBadanUsaha(dto.namaBadanUsaha());
        this.repository.save(agent);
        return this.util.sendResponse("sukses membuat agent", HttpStatus.CREATED, true, agent);
    }

    public ResponseEntity<Response> login(LoginDto dto) throws AgentNotFoundException{
        Agent agent = null;
        Optional<Agent> agentByEmail = this.repository.findByEmail(dto.email());
        if(agentByEmail.isEmpty()){
            Optional<Agent> agentByUsername = this.repository.findByUsername(dto.email());
            if(agentByUsername.isEmpty()){
                throw new AgentNotFoundException("data agent tidak ditemukan");
            } else {
                agent = agentByUsername.get();
            }
        } else {
            agent = agentByEmail.get();
        }
        if(this.bcrypt.matches(dto.password(), agent.getPassword())){
            String tokenJwt = this.jwt.generateToken(agent);
            Map<String, Object> data = new HashMap<>();
            data.put("agent", agent);
            data.put("jwt", tokenJwt);
            return this.util.sendResponse("user terautentikasi", HttpStatus.OK, true, data);
        } else {
            return this.util.sendResponse("user tidak terautentikasi", HttpStatus.UNAUTHORIZED, false, null);
        }
    }

    public ResponseEntity<Response> completeData(Long id, CompleteDataDto dto, MultipartFile file) throws AgentNotFoundException, IOException{
        Agent agent = this.repository.findById(id).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        agent.setAboutMe(dto.aboutMe());
        agent.setAlamatKantor(dto.alamatKantor());
        agent.setBio(dto.bio());
        agent.setKontakAdminKantor(dto.nomorTelepon());
        agent.setKontakPIC(dto.kontakWhatsappPIC());
        agent.setLinkAkunFacebook(dto.linkFacebook());
        agent.setLinkAkunInstagram(dto.linkInstagram());
        agent.setLinkAkunLine(dto.linkLine());
        agent.setLinkAkunTelegram(dto.linkTelegram());
        agent.setLinkAkunTwitter(dto.linkTwitter());
        agent.setNamaPIC(dto.namaPIC());
        agent.setStatusVerified(StatusVerified.MENUNGGU);
        agent.setSuratIzinUsaha(file.getBytes());
        agent.setWhatsappKantor(dto.kontakWhatsapp());
        this.repository.save(agent);
        return this.util.sendResponse("sukses menambahkan data tambahan agent", HttpStatus.OK, true, agent);
    }

    public ResponseEntity<Response> getAgent(Long id) throws AgentNotFoundException{
        Agent agent = this.repository.findById(id).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        return this.util.sendResponse("sukses mendapatkan agent", HttpStatus.OK, true, agent);
    }

    public ResponseEntity<Response> getAgentByTripId(Long tripId) throws TripNotFoundException{
        Trip trip = this.tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException("data trip tidak ditemukan"));
        Agent agent = trip.getAgent();
        agent.setTrip(List.of(trip));
        return this.util.sendResponse("sukses menemukan data agent", HttpStatus.OK, true, agent);
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

    public ResponseEntity<Response> isVerified(Long id) throws AgentNotFoundException{
        Agent agent = this.repository.findById(id).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        return this.util.sendResponse("sukses mendapatkan agent", HttpStatus.OK, true, agent.getStatusVerified());
    }

    public ResponseEntity<Response> updateAgent(Long id, UpdateAgentDto dto) throws AgentNotFoundException{
        Agent agent = this.repository.findById(id).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        agent.setEmail(dto.email());
        agent.setUsername(dto.username());
        this.repository.save(agent);
        return this.util.sendResponse("sukses mengupdate agent", HttpStatus.OK, true, agent);
    }

    public ResponseEntity<Response> updatePaymentStatus(Long id, int month) throws AgentNotFoundException{
        Agent agent = this.repository.findById(id).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan"));
        agent.setPremiumDate(agent.getPremiumDate().plusMonths(month));
        agent.setPaymentExpired(false);
        this.repository.save(agent);
        return this.util.sendResponse("sukses mengupdate status pembayaran agent", HttpStatus.OK, true, agent);
    }
}
