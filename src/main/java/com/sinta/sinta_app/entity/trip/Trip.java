package com.sinta.sinta_app.entity.trip;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sinta.sinta_app.entity.trip.deskripsi.Deskripsi;
import com.sinta.sinta_app.entity.trip.fasilitas.FasilitasTermasuk;
import com.sinta.sinta_app.entity.trip.fasilitas.FasilitasTidakTermasuk;

import lombok.Data;

@Entity
@Data
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String informasiPenting;

    private String linkRundown;

    private String catatanHarga;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trip")
    private Deskripsi deskripsi;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FasilitasTermasuk fasilitasTermasuk;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FasilitasTidakTermasuk fasilitasTidakTermasuk;

    @ElementCollection
    @CollectionTable
    @MapKey(name = "jumlah_peserta_tour")
    private Map<String, Object> harga;
}
