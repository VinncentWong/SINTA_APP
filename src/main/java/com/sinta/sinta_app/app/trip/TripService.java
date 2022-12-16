package com.sinta.sinta_app.app.trip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sinta.sinta_app.app.agent.AgentRepository;
import com.sinta.sinta_app.dto.trip.CreateTripDto;
import com.sinta.sinta_app.dto.trip.deskripsi.DetailDestinasiDto;
import com.sinta.sinta_app.entity.Response;
import com.sinta.sinta_app.entity.agent.Agent;
import com.sinta.sinta_app.entity.trip.Trip;
import com.sinta.sinta_app.entity.trip.deskripsi.Deskripsi;
import com.sinta.sinta_app.entity.trip.deskripsi.DetailDestinasi;
import com.sinta.sinta_app.entity.trip.fasilitas.FasilitasTermasuk;
import com.sinta.sinta_app.entity.trip.fasilitas.FasilitasTidakTermasuk;
import com.sinta.sinta_app.exception.AgentNotFoundException;
import com.sinta.sinta_app.exception.TripNotFoundException;
import com.sinta.sinta_app.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TripService {
    
    private final TripRepository repository;

    private final ResponseUtil util;

    private final AgentRepository agentRepository;

    @Autowired
    public TripService(TripRepository repository, ResponseUtil util, AgentRepository agentRepository){
        this.repository = repository;
        this.util = util;
        this.agentRepository = agentRepository;
    }

    public ResponseEntity<Response> createTrip(Long idAgent, CreateTripDto dto, List<MultipartFile> listPhoto, MultipartFile cover) throws IOException, AgentNotFoundException{
        Agent agent = this.agentRepository.findById(idAgent).orElseThrow(() -> new AgentNotFoundException("data agent tidak ditemukan di database!"));
        // membuat trip
        Trip trip = new Trip();
        trip.setInformasiPenting(dto.informasiPenting());
        trip.setCatatanHarga(dto.catatanHarga());
        trip.setLinkRundown(dto.linkRundown());
        trip.setAgent(agent);

        // membuat deskripsi
        Deskripsi deskripsi = new Deskripsi();
        deskripsi.setCover(cover.getBytes());

        // membuat detail destinasi
        List<DetailDestinasi> listDetailDestinasi = new ArrayList<>();
        boolean serviceRunSuccessful = true;
        for(DetailDestinasiDto destinasi: dto.deskripsi().detailDestinasi()){
            int j = 0;
            try{
                DetailDestinasi detailDestinasi = new DetailDestinasi();
                detailDestinasi.setDescription(destinasi.penjelasan());
                detailDestinasi.setTitle(destinasi.judul());
                detailDestinasi.setImage(listPhoto.get(j).getBytes());
                detailDestinasi.setDeskripsi(deskripsi);
                listDetailDestinasi.add(detailDestinasi);
                j++;
            } catch(IOException ex){
                serviceRunSuccessful = false;
                break;
            }
        }
        if(!serviceRunSuccessful){
            return this.util.sendResponse("terjadi kesalahan ketika mengolah gambar", HttpStatus.INTERNAL_SERVER_ERROR, false, null);
        }

        // set deskripsi
        deskripsi.setDetailDestinasi(listDetailDestinasi);
        deskripsi.setJudul(dto.deskripsi().judul());
        deskripsi.setTrip(trip);

        // membuat fasilitas termasuk
        List<FasilitasTermasuk> listFasilitasTermasuk = new ArrayList<>();
        dto.fasilitasTermasuk().forEach((fasilitas) -> {
            FasilitasTermasuk fasilitasTermasuk = new FasilitasTermasuk();
            fasilitasTermasuk.setFasilitasTermasuk(fasilitas.poinFasilitas());
            fasilitasTermasuk.setTrip(trip);
            listFasilitasTermasuk.add(fasilitasTermasuk);
        });

        // membuat fasilitas tidak termasuk
        List<FasilitasTidakTermasuk> listFasilitasTidakTermasuk = new ArrayList<>();
        dto.fasilitasTidakTermasuk().forEach((fasilitas) -> {
            FasilitasTidakTermasuk fasilitasTidakTermasuk = new FasilitasTidakTermasuk();
            fasilitasTidakTermasuk.setFasilitasTermasuk(fasilitas.poinFasilitas());
            fasilitasTidakTermasuk.setTrip(trip);
            listFasilitasTidakTermasuk.add(fasilitasTidakTermasuk);
        });
        
        
        log.info(dto.harga().toString());
        // membuat properti informasi harga dan jumlah orang
        Map<String, Integer> harga = new LinkedHashMap<>();
        dto.harga().forEach((hargas) -> {
            harga.put(hargas.jumlahPesertaTour(), hargas.harga());
        });
        log.info(harga.toString());

        trip.setDeskripsi(deskripsi);
        trip.setFasilitasTermasuk(listFasilitasTermasuk);
        trip.setFasilitasTidakTermasuk(listFasilitasTidakTermasuk);
        trip.setHarga(harga);
        agent.getTrip().add(trip);
        return this.util.sendResponse("sukses membuat trip", HttpStatus.CREATED, true, trip);
    }

    public ResponseEntity<Response> getTrip(Long idAgent, Long idTrip) throws TripNotFoundException{
        Trip trip = this.repository.find(idAgent, idTrip).orElseThrow(() -> new TripNotFoundException("data trip tidak ditemukan di database!"));
        return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, trip);
    }

    public ResponseEntity<Response> getTrip(Long idAgent) throws TripNotFoundException{
       List<Trip> trip = this.repository.find(idAgent).orElseThrow(() -> new TripNotFoundException());
       return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, trip);
    }

    public ResponseEntity<Response> getPhotoTrip(Long idTrip) throws TripNotFoundException{
        Trip trip = this.repository.findById(idTrip).orElseThrow(() -> new TripNotFoundException());
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("image_cover", trip.getDeskripsi().getCover());
        List<byte[]> list = new ArrayList<>();
        trip.getDeskripsi().getDetailDestinasi().forEach((x) -> {
            list.add(x.getImage());
        });
        response.put("image_detail_destination", list);
        return this.util.sendResponse("sukses mendapatkan photo", HttpStatus.OK, true, response);
    }
}
