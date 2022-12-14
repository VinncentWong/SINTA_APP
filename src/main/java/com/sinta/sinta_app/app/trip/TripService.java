package com.sinta.sinta_app.app.trip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.sinta.sinta_app.entity.trip.KategoriTrip;
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
        trip.setKota(dto.kota());
        trip.setLamaTrip(dto.lamaTrip());
        trip.setNeedRequirement(dto.needRequirement());
        trip.setProvinsi(dto.provinsi());
        trip.setKategoriTrip(dto.kategoriTrip());

        // membuat deskripsi
        Deskripsi deskripsi = new Deskripsi();
        deskripsi.setCover(cover.getBytes());

        // membuat detail destinasi
        List<DetailDestinasi> listDetailDestinasi = new ArrayList<>();
        boolean serviceRunSuccessful = true;
        int j = 0;
        for(DetailDestinasiDto destinasi: dto.deskripsi().detailDestinasi()){
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

        Map<String, Integer> listHargaSorted = harga.entrySet().stream()
                                                .sorted(Map.Entry.comparingByValue())
                                                .collect(Collectors.toMap(
                                                    (key) -> key.getKey(),
                                                    (value) -> value.getValue(),
                                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new
                                                ));
        trip.setDeskripsi(deskripsi);
        trip.setFasilitasTermasuk(listFasilitasTermasuk);
        trip.setFasilitasTidakTermasuk(listFasilitasTidakTermasuk);
        trip.setHarga(listHargaSorted);
        agent.getTrip().add(trip);
        return this.util.sendResponse("sukses membuat trip", HttpStatus.CREATED, true, trip);
    }

    public ResponseEntity<Response> getTrip() throws TripNotFoundException{
        Iterable<Trip> iterable = this.repository.findAll();
        if(iterable instanceof List<Trip> list){
            if(list.size() == 0){
                throw new TripNotFoundException("data trip tidak ditemukan!");
            } else {
                return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, list);
            }
        } else {
            return this.util.sendResponse("class cast exception pada collection trip", HttpStatus.INTERNAL_SERVER_ERROR, false, null);
        }
    }

    public ResponseEntity<Response> getTrip(Long idAgent, Long idTrip) throws TripNotFoundException{
        Trip trip = this.repository.find(idAgent, idTrip).orElseThrow(() -> new TripNotFoundException("data trip tidak ditemukan di database!"));
        return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, trip);
    }

    public ResponseEntity<Response> getTrip(Long idAgent) throws TripNotFoundException{
       List<Trip> trip = this.repository.find(idAgent);
       return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, trip);
    }
    
    public ResponseEntity<Response> getTripByMaximalPrice(Long price){
        Iterable<Trip> iterable = this.repository.findAll();
        if(iterable instanceof List<Trip> list){
            if(list.isEmpty()){
                return this.util.sendResponse("data trip tidak ditemukan", HttpStatus.INTERNAL_SERVER_ERROR, false, null);
            } else {
                List<Trip> filteredList = new ArrayList<>();
                list.forEach((trip) -> {
                    boolean valid = trip.getHarga()
                                    .entrySet()
                                    .stream()
                                    .anyMatch((entry) -> {
                                        if(entry == null){
                                            return false;
                                        } else {
                                            if(entry.getValue() > price){
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        }
                                    });
                    if(valid){
                        filteredList.add(trip);
                    }
                });
                return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, filteredList);
            }
        } else {
            return this.util.sendResponse("class cast exception pada collection trip", HttpStatus.INTERNAL_SERVER_ERROR, false, null);
        }
    }

    public ResponseEntity<Response> getTrip(KategoriTrip kategoriTrip) throws TripNotFoundException{
        List<Trip> trip = this.repository.findByCategory(kategoriTrip.ordinal());
        if(trip.size() == 0){
            throw new TripNotFoundException("data trip tidak ditemukan");
        }
        return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, trip);
    }

    public ResponseEntity<Response> getTrip(boolean requirement) throws TripNotFoundException{
        List<Trip> trip = this.repository.find(requirement);
        if(trip.size() == 0){
            throw new TripNotFoundException("data trip tidak ditemukan");
        }
        return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, trip);
    }

    public ResponseEntity<Response> getTrip(Long maxPrice, KategoriTrip kategoriTrip) throws TripNotFoundException{
        List<Trip> trip = this.repository.find(maxPrice, kategoriTrip.ordinal());
        if(trip.size() == 0){
            throw new TripNotFoundException("data trip tidak ditemukan");
        }
        return this.util.sendResponse("sukses mendapatkan trip", HttpStatus.OK, true, trip);
    }
}
