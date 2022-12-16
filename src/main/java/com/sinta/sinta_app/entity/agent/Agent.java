package com.sinta.sinta_app.entity.agent;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinta.sinta_app.entity.Role;
import com.sinta.sinta_app.entity.trip.Trip;

import lombok.Data;

@Entity
@Data
public class Agent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String namaBadanUsaha;

    private String namaPIC;

    private String kontakPIC;

    private String alamatKantor;

    private String kontakKantor;

    private String whatsappKantor;

    private String akunInstagram;

    @JsonIgnore
    private byte[] suratIzinUsaha;

    private String linkGmaps;

    private String akunTelegram;

    private String akunFacebook;

    private String akunLine;

    private String akunTwitter;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    private LocalDate deletedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
    private List<Trip> trip;

    private final Role role = Role.AGENT;
}
