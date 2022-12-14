package com.sinta.sinta_app.entity.trip.fasilitas;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinta.sinta_app.entity.trip.Trip;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "trip")
public class FasilitasTidakTermasuk {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fasilitasTermasuk;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Trip trip;
}
