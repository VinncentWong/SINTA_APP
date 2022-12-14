package com.sinta.sinta_app.entity.trip.fasilitas;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import com.sinta.sinta_app.entity.trip.Trip;

import lombok.Data;

@Entity
@Data
public class FasilitasTermasuk{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fasilitasTermasuk;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trip trip;
}