package com.sinta.sinta_app.entity.trip.deskripsi;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinta.sinta_app.entity.trip.Trip;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "trip")
public class Deskripsi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String judul;

    @Lob
    private byte[] cover;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DetailDestinasi detailDestinasi;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Trip trip;

}
