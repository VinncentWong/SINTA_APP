package com.sinta.sinta_app.app.trip;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sinta.sinta_app.dto.trip.CreateTripDto;
import com.sinta.sinta_app.entity.Response;
import com.sinta.sinta_app.entity.trip.KategoriTrip;
import com.sinta.sinta_app.exception.AgentNotFoundException;
import com.sinta.sinta_app.exception.TripNotFoundException;
import com.sinta.sinta_app.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/trip")
public class TripController {
    
    private final TripService service;

    private final ResponseUtil util;

    @Autowired
    public TripController(TripService service, ResponseUtil util){
        this.service = service;
        this.util = util;
    }

    @PostMapping(value = "/create/{idAgent}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> createTrip(
        @PathVariable("idAgent") Long id, 
        @RequestPart("trip") @Valid CreateTripDto dto, 
        @RequestPart("image_cover") MultipartFile imageCoverFile, 
        @RequestPart("image_detail_destination") List<MultipartFile> imageDetailDestination) throws IOException, AgentNotFoundException{
        return this.service.createTrip(id, dto, imageDetailDestination, imageCoverFile);
    }

    @GetMapping("/getall")
    public ResponseEntity<Response> getTrip() throws TripNotFoundException{
        return this.service.getTrip();
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getTrip(@RequestParam("idAgent") Long idAgent, @RequestParam("idTrip") Long idTrip) throws TripNotFoundException{
        return this.service.getTrip(idAgent, idTrip);
    }

    @GetMapping("/get/{idAgent}")
    public ResponseEntity<Response> getTrip(@PathVariable("idAgent") Long idAgent) throws TripNotFoundException{
        return this.service.getTrip(idAgent);
    }

    @GetMapping("/get/price")
    public ResponseEntity<Response> getTripByMaximalPrice(@RequestParam("maxPrice") Long maxPrice){
        return this.service.getTripByMaximalPrice(maxPrice);
    }

    @GetMapping("/get/kategori")
    public ResponseEntity<Response> getTrip(@RequestParam("kategori") String kategoriTrip) throws TripNotFoundException{
        KategoriTrip kategori = switch (kategoriTrip) {
            case "lokal", "Lokal"-> KategoriTrip.LOKAL;
            case "internasional", "Internasional" -> KategoriTrip.INTERNASIONAL;
            default -> null;
        };
        if(kategori == null){
            return this.util.sendResponse("RequestParam kategori tidak valid", HttpStatus.BAD_REQUEST, true, null);
        }
        return this.service.getTrip(kategori);
    }

    @GetMapping("/get/requirement")
    public ResponseEntity<Response> getTripByRequirement(@RequestParam("requirement") boolean requirement) throws TripNotFoundException{
        return this.service.getTrip(requirement);
    }
}
