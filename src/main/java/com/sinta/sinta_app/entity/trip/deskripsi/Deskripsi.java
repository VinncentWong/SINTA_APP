package com.sinta.sinta_app.entity.trip.deskripsi;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sinta.sinta_app.entity.trip.Trip;

import lombok.Data;

@Entity
@Data
public class Deskripsi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String judul;

    @Lob
    private byte[] cover;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DetailDestinasi detailDestinasi;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trip trip;

}
