package com.sinta.sinta_app.entity.trip;

import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinta.sinta_app.entity.agent.Agent;
import com.sinta.sinta_app.entity.trip.deskripsi.Deskripsi;
import com.sinta.sinta_app.entity.trip.fasilitas.FasilitasTermasuk;
import com.sinta.sinta_app.entity.trip.fasilitas.FasilitasTidakTermasuk;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "agent")
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String informasiPenting;

    private String linkRundown;

    private String catatanHarga;

    private String kota;

    private String provinsi;

    private String lamaTrip;

    private Boolean needRequirement;

    private KategoriTrip kategoriTrip;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trip")
    private Deskripsi deskripsi;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trip")
    private List<FasilitasTermasuk> fasilitasTermasuk;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trip")
    private List<FasilitasTidakTermasuk> fasilitasTidakTermasuk;

    @ElementCollection
    @CollectionTable(name = "harga", joinColumns = @JoinColumn(name = "id_trip"))
    @MapKeyColumn(name = "jumlah_orang")
    private Map<String, Integer> harga;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Agent agent;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    private LocalDate deletedAt;
}
