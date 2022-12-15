package com.sinta.sinta_app.entity.trip;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
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
    private List<FasilitasTermasuk> fasilitasTermasuk;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FasilitasTidakTermasuk> fasilitasTidakTermasuk;

    @ElementCollection
    @CollectionTable(name = "harga", joinColumns = @JoinColumn(name = "id_trip"))
    @MapKeyColumn(name = "jumlah_orang")
    private Map<String, Integer> harga;
}
